package com.ljy.flightreservation.services.member.application;

import com.ljy.flightreservation.services.member.MemberIntegrationTest;
import com.ljy.flightreservation.services.member.application.exception.NoChangedMemberException;
import com.ljy.flightreservation.services.member.application.model.*;
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
public class MemberService_Test extends MemberIntegrationTest {
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
    void changePassword() throws NoChangedMemberException {
        // given
        newMember(aMember().id(MemberId.of("changememberid")).password(Password.of("password", passwordEncoder)));

        // when
        MemberModel memberModel = memberService.changeMember(ChangeMember.builder()
                        .changePassword(ChangePassword.builder()
                                .changePassword("changepassword")
                                .originPassword("password")
                                .build())
                .build(), MemberId.of("changememberid"));

        // then
        assertTrue(passwordEncoder.matches("changepassword", memberModel.getPassword()));
    }

    @Test
    @DisplayName("여권번호 변경")
    void changePassport() throws NoChangedMemberException {
        // given
        newMember(aMember().id(MemberId.of("changememberid")).password(Password.of("password", passwordEncoder)));

        // when
        MemberModel memberModel = memberService.changeMember(ChangeMember.builder()
                    .passport("b10382738")
                .build(), MemberId.of("changememberid"));

        // then
        assertEquals(memberModel.getMemberInfo().getPassport(), "b10382738");
    }

    @Test
    @DisplayName("이메일 변경")
    void changeEmail() throws NoChangedMemberException {
        // given
        newMember(aMember().id(MemberId.of("changememberid")).password(Password.of("password", passwordEncoder)));

        // when
        MemberModel memberModel = memberService.changeMember(ChangeMember.builder()
                .email("changeemail@google.com")
                .build(), MemberId.of("changememberid"));

        // then
        assertEquals(memberModel.getMemberInfo().getEmail(), "changeemail@google.com");
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

    @Test
    @DisplayName("입금")
    void deposit(){
        // given
        newMember(aMember().id(MemberId.of("depositmember")));

        // when
        MemberModel memberModel = memberService.deposit(DepositMoney.builder().money(3000).build(), MemberId.of("depositmember"));

        // then
        assertEquals(memberModel.getMoney(), 3000);
    }

}
