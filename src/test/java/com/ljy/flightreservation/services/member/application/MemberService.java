package com.ljy.flightreservation.services.member.application;

import com.ljy.flightreservation.services.member.MemberMapper;
import com.ljy.flightreservation.services.member.RegisterMember;
import com.ljy.flightreservation.services.member.application.model.ChangePassword;
import com.ljy.flightreservation.services.member.application.model.WithdrawalMember;
import com.ljy.flightreservation.services.member.domain.Member;
import com.ljy.flightreservation.services.member.domain.MemberRepository;
import com.ljy.flightreservation.services.member.domain.RegisterMemberValidator;
import com.ljy.flightreservation.services.member.domain.model.MemberModel;
import com.ljy.flightreservation.services.member.domain.value.MemberId;
import com.ljy.flightreservation.services.member.domain.value.Password;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * # 회원 비밀번호 변경
     */
    public MemberModel changePassword(ChangeMember changeMember, MemberId changememberId) {
        Member member = MemberServiceHelper.getMember(memberRepository, changememberId);
        if(changeMember.getChangePassword() != null){
            ChangePassword changePassword = changeMember.getChangePassword();
            member.changePassword(changePassword.getOriginPassword(), Password.of(changePassword.getChangePassword(), memberMapper.getPasswordEncoder()), memberMapper.getPasswordEncoder());
            log.info("change member password : {}", changememberId);
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

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
