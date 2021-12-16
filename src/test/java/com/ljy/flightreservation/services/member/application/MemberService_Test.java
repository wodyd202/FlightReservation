package com.ljy.flightreservation.services.member.application;

import com.ljy.flightreservation.services.member.ChangeMemberInfo;
import com.ljy.flightreservation.services.member.RegisterMember;
import com.ljy.flightreservation.services.member.application.model.ChangePassword;
import com.ljy.flightreservation.services.member.application.model.WithdrawalMember;
import com.ljy.flightreservation.services.member.domain.exception.AlreadyExistMemberException;
import com.ljy.flightreservation.services.member.domain.model.MemberModel;
import com.ljy.flightreservation.services.member.domain.value.MemberId;
import com.ljy.flightreservation.services.member.domain.value.MemberState;
import com.ljy.flightreservation.services.member.domain.value.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ljy.flightreservation.services.member.MemberFixture.aMember;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberService_Test extends MemberAPITest{
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 등록")
    void registerMember(){
        // given
        RegisterMember registerMember = RegisterMember.builder()
                .id("testregisterid")
                .password("password")
                .memberInfo(ChangeMemberInfo.builder()
                        .email("email@google.com")
                        .passport("")
                        .build())
                .build();

        // when
        MemberModel memberModel = memberService.register(registerMember);

        // then
        assertEquals(memberModel.getId(), "testregisterid");
        assertTrue(passwordEncoder.matches("password", memberModel.getPassword()));
        assertEquals(memberModel.getMemberInfo().getEmail(), "email@google.com");
        assertEquals(memberModel.getMemberInfo().getPassport(), null);
    }

    @Test
    @DisplayName("중복된 아이디가 존재할 경우 에러")
    void alreadyExistMember(){
        // given
        RegisterMember registerMember = RegisterMember.builder()
                .id("testregisterid")
                .password("password")
                .memberInfo(ChangeMemberInfo.builder()
                        .email("email@google.com")
                        .passport("")
                        .build())
                .build();

        // when
        assertThrows(AlreadyExistMemberException.class, ()->{
            memberService.register(registerMember);
        });
    }

    @Test
    @DisplayName("비밀번호 변경")
    void changePassword(){
        // given
        newMember(aMember().id(MemberId.of("changememberid")).password(Password.of("password", passwordEncoder)));

        // when
        MemberModel memberModel = memberService.changePassword(ChangeMember.builder()
                        .changePassword(ChangePassword.builder()
                                .changePassword("changepassword")
                                .originPassword("password")
                                .build())
                .build(), MemberId.of("changememberid"));

        // then
        assertTrue(passwordEncoder.matches("changepassword", memberModel.getPassword()));
    }

    @Test
    @DisplayName("회원 탈퇴")
    void withdrawal(){
        // given
        newMember(aMember().id(MemberId.of("withdrawalid")).password(Password.of("password", passwordEncoder)));

        // when
        MemberModel memberModel = memberService.withdrawal(WithdrawalMember.builder().originPassword("password").build(), MemberId.of("withdrawalid"));

        // then
        assertEquals(memberModel.getState(), MemberState.DELETED);
    }

}
