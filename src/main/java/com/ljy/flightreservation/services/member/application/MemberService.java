package com.ljy.flightreservation.services.member.application;

import com.ljy.flightreservation.services.member.application.exception.NoChangedMemberException;
import com.ljy.flightreservation.services.member.application.model.*;
import com.ljy.flightreservation.services.member.domain.Member;
import com.ljy.flightreservation.services.member.domain.MemberRepository;
import com.ljy.flightreservation.services.member.domain.RegisterMemberValidator;
import com.ljy.flightreservation.services.member.domain.exception.MemberNotFoundException;
import com.ljy.flightreservation.services.member.domain.model.MemberModel;
import com.ljy.flightreservation.services.member.domain.value.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    // util
    private final MemberMapper memberMapper;
    private final RegisterMemberValidator registerMemberValidator;

    /**
     * @param registerMember
     * # 회원 등록
     */
    public MemberModel register(RegisterMember registerMember) {
        Member member = memberMapper.mapFrom(registerMember);
        member.register(registerMemberValidator);

        memberRepository.save(member);

        MemberModel memberModel = member.toModel();
        log.info("save member into database : {}", memberModel);
        return memberModel;
    }

    /**
     * @param changeMember
     * @param changememberId
     * # 회원 변경
     */
    public MemberModel changeMember(ChangeMember changeMember, MemberId changememberId) throws NoChangedMemberException {
        Member member = MemberServiceHelper.getMember(memberRepository, changememberId);

        boolean isChangedPassword = false;
        boolean isChangedPassport = false;
        boolean isChangedEmail = false;

        // 회원 비밀번호 변경
        if(changeMember.getChangePassword() != null){
            ChangePassword changePassword = changeMember.getChangePassword();
            isChangedPassword = member.changePassword(changePassword.getOriginPassword(), Password.of(changePassword.getChangePassword(), memberMapper.getPasswordEncoder()), memberMapper.getPasswordEncoder());
            log.info("change member password : {}", changememberId);
        }

        // 여권 번호 변경
        if(changeMember.getPassport() != null){
            isChangedPassport = member.changePassport(registerMemberValidator.getPassportValidator(), Passport.of(changeMember.getPassport()));
            log.info("change member passport : {}", changememberId);
        }

        // 이메일 변경
        if(changeMember.getEmail() != null){
            isChangedEmail = member.changeEmail(Email.of(changeMember.getEmail()));
            log.info("change member email : {}", changememberId);
        }

        if(!isChangedPassword && !isChangedPassport && !isChangedEmail){
            throw new NoChangedMemberException();
        }

        memberRepository.save(member);
        return member.toModel();
    }

    /**
     * @param withdrawal
     * @param withdrawalId
     * # 회원 탈퇴
     */
    public MemberModel withdrawal(WithdrawalMember withdrawal, MemberId withdrawalId) {
        Member member = MemberServiceHelper.getMember(memberRepository, withdrawalId);

        member.withdrawal(withdrawal.getOriginPassword(), memberMapper.getPasswordEncoder());

        memberRepository.save(member);
        log.info("withdrawal member : {}", withdrawalId);
        MemberModel memberModel = member.toModel();
        return memberModel;
    }

    /**
     * @param depositMoney
     * @param memberId
     * # 입금
     */
    public MemberModel deposit(DepositMoney depositMoney, MemberId memberId) {
        Member member = MemberServiceHelper.getMember(memberRepository, memberId);

        member.deposit(Money.won(depositMoney.getMoney()));

        memberRepository.save(member);
        log.info("member {} diposit money : {}", memberId, depositMoney.getMoney());
        MemberModel memberModel = member.toModel();
        return memberModel;
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        MemberModel member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        if(member.isDeleted()){
            throw new MemberNotFoundException();
        }
        return new User(member.getId(), member.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_MEMBER")));
    }

    /**
     * @param memberId
     * # 회원 조회
     */
    public MemberModel getMember(String memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }
}
