package com.ljy.flightreservation.services.member.domain;

import com.ljy.flightreservation.services.member.application.model.ChangeMemberInfo;
import com.ljy.flightreservation.services.member.application.MemberMapper;
import com.ljy.flightreservation.services.member.application.model.RegisterMember;
import com.ljy.flightreservation.services.member.StubMemberRepository;
import com.ljy.flightreservation.services.member.domain.exception.*;
import com.ljy.flightreservation.services.member.domain.model.MemberModel;
import com.ljy.flightreservation.services.member.domain.value.*;
import com.ljy.flightreservation.services.member.infrastructure.StubPassportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ljy.flightreservation.services.member.MemberFixture.aMember;
import static com.ljy.flightreservation.services.member.MemberFixture.passwordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

/**
 * 회원 도메인 테스트
 */
public class Member_Test {

    @ParameterizedTest
    @DisplayName("숫자 및 영문만 입력 가능")
    @ValueSource(strings = {"실패","  test", " test ", "test ", "ㄱㄴㄷㄹ"})
    void invalidId1(String id) {
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            MemberId.of(id);
        });
    }

    @ParameterizedTest
    @DisplayName("회원 아이디는 4자 이상 15자 이하만 허용")
    @ValueSource(strings = {"abc", "abcdefgabcdefgabcdefg"})
    void invalidId2(String id){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            MemberId.of(id);
        });
    }

    @Test
    @DisplayName("아이디는 비워져 있으면 안됨")
    void emptyId(){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
           MemberId.of("");
        });
    }

    @Test
    @DisplayName("아이디 형식이 유효함")
    void successId(){
        // when
        MemberId id = MemberId.of("test1234");

        // then
        assertEquals(id.get(), "test1234");
    }

    @Test
    @DisplayName("비밀번호 형식이 유효함")
    void successPassword(){
        // given
        PasswordEncoder passwordEncoder = createDelegatingPasswordEncoder();

        // when
        Password password = Password.of("password", passwordEncoder);

        // then
        assertTrue(passwordEncoder.matches("password", password.get()));
    }

    @ParameterizedTest
    @DisplayName("비밀번호는 5자 이상 15자 이하만 허용")
    @ValueSource(strings = {"1234","1234567890123456"})
    void invalidPassword(String password){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            Password.of(password, null);
        });
    }

    @ParameterizedTest
    @DisplayName("이메일 형식이 유효해야함")
    @ValueSource(strings = {"invalid", "invalid@","@invalid.com"})
    void invalidEmail(String email){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            Email.of(email);
        });
    }

    @Test
    @DisplayName("이메일은 빈값을 허용하지 않음")
    void emptyEmail(){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            Email.of("");
        });
    }

    @Test
    @DisplayName("이메일 형식이 유효함")
    void successEmail(){
        // when
        Email email = Email.of("test@test.com");

        // then
        assertEquals(email.get(), "test@test.com");
    }

    @ParameterizedTest
    @DisplayName("여권 번호는 빈값을 허용하지만 빈값이 아닌 경우 유효해야함")
    @ValueSource(strings = {"invalid", "가나다", "Xz0382738"})
    void invalidPassport(String passport){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
           new Passport(passport);
        });
    }

    @Test
    @DisplayName("여권 번호는 빈값 허용")
    void emptyPassport(){
        // when
        Passport passport = new Passport("");

        // then
        assertEquals(passport, new Passport(""));
    }

    @Test
    @DisplayName("사용자 생성")
    void newUser(){
        // given
        RegisterMember registerMember = RegisterMember.builder()
                    .id("usertestid")
                    .password("password")
                    .memberInfo(ChangeMemberInfo.builder()
                            .email("email@naver.com")
                            .passport("")
                            .build())
                    .build();
        MemberMapper memberMapper = new MemberMapper();
        memberMapper.setPasswordEncoder(passwordEncoder);

        // when
        Member member = memberMapper.mapFrom(registerMember);
        MemberModel memberModel = member.toModel();

        // then
        assertEquals(memberModel.getId(), "usertestid");
        assertTrue(passwordEncoder.matches("password", memberModel.getPassword()));
        assertEquals(memberModel.getMemberInfo().getPassport(), null);
        assertEquals(memberModel.getMemberInfo().getEmail(), "email@naver.com");
    }

    @Test
    @DisplayName("해당 아이디를 가진 사용자가 이미 존재함")
    void alreadyExistUser(){
        // given
        MemberRepository memberRepository = new StubMemberRepository();
        PassportValidator passportValidator = mock(PassportValidator.class);
        RegisterMemberValidator registerMemberValidator = new RegisterMemberValidator(memberRepository, passportValidator);
        memberRepository.save(aMember().id(MemberId.of("existmember")).build());

        // when
        assertThrows(AlreadyExistMemberException.class, ()->{
            registerMemberValidator.validation(MemberId.of("existmember"), mock(MemberInfo.class));
        });
    }

    @Test
    @DisplayName("사용자 생성시 여권번호를 입력했지만 여권번호가 유효하지 않음")
    void invalidPassportWhenRegister(){
        // given
        PassportRepository passportRepository = new StubPassportRepository();
        PassportValidator passportValidator = new PassportValidator(passportRepository);

        // when
        assertThrows(IllegalArgumentException.class, ()->{
            passportValidator.validation(Passport.of("a10382738"));
        });
    }

    @Test
    @DisplayName("비밀번호 변경")
    void changePassword(){
        // given
        Password password = Password.of("password", passwordEncoder);
        Member member = aMember().password(password).build();

        // when
        member.changePassword("password", Password.of("changePassword", passwordEncoder), passwordEncoder);
        MemberModel memberModel = member.toModel();

        // then
        assertTrue(passwordEncoder.matches("changePassword", memberModel.getPassword()));
    }

    @Test
    @DisplayName("비밀번호 변경시 기존 비밀번호가 일치하지 않음")
    void notEqOriginPasswordWhenChangePassword(){
        // given
        Password password = Password.of("password", passwordEncoder);
        Member member = aMember().password(password).build();

        // when
        assertThrows(IllegalArgumentException.class, ()->{
            member.changePassword("notequalspassword", Password.of("changePassword", passwordEncoder), passwordEncoder);
        });
    }

    @Test
    @DisplayName("여권 번호 변경")
    void changePassport() {
        // given
        PassportRepository passportRepository = new StubPassportRepository();
        PassportValidator passportValidator = new PassportValidator(passportRepository);
        Member member = aMember().build();

        // when
        member.changePassport(passportValidator, new Passport("X10382738"));
        MemberModel memberModel = member.toModel();

        // then
        assertEquals(memberModel.getMemberInfo().getPassport(), "X10382738");
    }

    @Test
    @DisplayName("여권 번호 변경시 여권번호가 유효하지 않음")
    void invalidPassportWhenChangePassport(){
        // given
        PassportRepository passportRepository = new StubPassportRepository();
        PassportValidator passportValidator = new PassportValidator(passportRepository);
        Member member = aMember().build();

        // when
        assertThrows(IllegalArgumentException.class, ()->{
            member.changePassport(passportValidator, new Passport("a10382738"));
        });
    }

    @Test
    @DisplayName("회원 탈퇴시 기존 비밀번호가 일치하지 않음")
    void notEqOriginPasswordWhenWithdrawal(){
        // given
        Member member = aMember().password(Password.of("password", passwordEncoder)).build();

        // when
        assertThrows(IllegalArgumentException.class, ()-> {
            member.withdrawal("notEqPassword", passwordEncoder);
        });
    }

    @Test
    @DisplayName("회원 탈퇴")
    void withdrawal(){
        // given
        Member member = aMember().password(Password.of("password", passwordEncoder)).build();

        // when
        member.withdrawal("password", passwordEncoder);
        MemberModel memberModel = member.toModel();

        // then
        assertEquals(memberModel.getState(), MemberState.DELETED);
    }

    @Test
    @DisplayName("입금")
    void deposit(){
        // given
        Member member = aMember().build();

        // when
        member.deposit(Money.won(30000L));
        MemberModel memberModel = member.toModel();

        // then
        assertEquals(30000L, memberModel.getMoney());
    }

    @Test
    @DisplayName("지불")
    void pay(){
        // given
        Member member = aMember().build();
        member.deposit(Money.won(30000L));

        // when
        member.pay(Money.won(3000L));
        MemberModel memberModel = member.toModel();

        // then
        assertEquals(27000L, memberModel.getMoney());
    }

}
