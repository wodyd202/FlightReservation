package com.ljy.flightreservation.services.member.domain;

import com.ljy.flightreservation.services.member.domain.exception.AlreadyExistMemberException;
import com.ljy.flightreservation.services.member.domain.value.MemberInfo;
import com.ljy.flightreservation.services.member.domain.value.MemberId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 멤버 등록 validator
 */
@Component
@AllArgsConstructor
public class RegisterMemberValidator {
    private final MemberRepository memberRepository;
    private final PassportValidator passportValidator;

    public void validation(MemberId id, MemberInfo memberInfo) {
        verifyNotExistMember(id);

        // 사용자가 여권 번호를 입력했을 경우에만 validation
        if(!memberInfo.getPassport().isEmpty()){
            passportValidator.validation(memberInfo.getPassport());
        }
    }

    private void verifyNotExistMember(MemberId id) {
        Optional<Member> findUser = getMember(id);
        if(findUser.isPresent()){
            throw new AlreadyExistMemberException("이미 해당 아이디의 회원이 존재합니다.");
        }
    }

    private Optional<Member> getMember(MemberId id) {
        return memberRepository.findById(id);
    }
}
