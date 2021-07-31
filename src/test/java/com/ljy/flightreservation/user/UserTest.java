package com.ljy.flightreservation.user;

import com.ljy.flightreservation.user.command.application.PassportRepository;
import com.ljy.flightreservation.user.command.application.UserRepository;
import com.ljy.flightreservation.user.command.domain.agg.PassportValidator;
import com.ljy.flightreservation.user.command.domain.agg.RegisterUserValidator;
import com.ljy.flightreservation.user.command.domain.agg.User;
import com.ljy.flightreservation.user.command.domain.exception.*;
import com.ljy.flightreservation.user.command.domain.value.Email;
import com.ljy.flightreservation.user.command.domain.value.Passport;
import com.ljy.flightreservation.user.command.domain.value.Password;
import com.ljy.flightreservation.user.command.domain.value.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

public class UserTest {

    @ParameterizedTest
    @DisplayName("숫자 및 영문만 입력 가능")
    @ValueSource(strings = {"실패","  test", " test ", "test ", "ㄱㄴㄷㄹ"})
    void invalidId1(String id) {
        assertThrows(InvalidUserIdException.class, ()->{
            new UserId(id);
        });
    }

    @ParameterizedTest
    @DisplayName("회원 아이디는 4자 이상 15자 이하만 허용")
    @ValueSource(strings = {"abc", "abcdefgabcdefgabcdefg"})
    void invalidId2(String id){
        assertThrows(InvalidUserIdException.class, ()->{
            new UserId(id);
        });
    }

    @Test
    @DisplayName("아이디는 비워져 있으면 안됨")
    void emptyId(){
        assertThrows(InvalidUserIdException.class, ()->{
           new UserId("");
        });
    }

    @Test
    @DisplayName("아이디 형식이 유효함")
    void successId(){
        UserId id = new UserId("test1234");
        assertEquals(id, new UserId("test1234"));
    }

    @Test
    @DisplayName("비밀번호 형식이 유효함")
    void successPassword(){
        PasswordEncoder passwordEncoder = createDelegatingPasswordEncoder();
        Password password = new Password("password", passwordEncoder);
        assertTrue(passwordEncoder.matches("password", password.get()));
    }

    @ParameterizedTest
    @DisplayName("비밀번호는 5자 이상 15자 이하만 허용")
    @ValueSource(strings = {"1234","1234567890123456"})
    void invalidPassword(String password){
        assertThrows(InvalidPasswordException.class, ()->{
            new Password(password, null);
        });
    }

    @ParameterizedTest
    @DisplayName("이메일 형식이 유효해야함")
    @ValueSource(strings = {"invalid", "invalid@","@invalid.com"})
    void invalidEmail(String email){
        assertThrows(InvalidEmailException.class, ()->{
            new Email(email);
        });
    }

    @Test
    @DisplayName("이메일은 빈값을 허용하지 않음")
    void emptyEmail(){
        assertThrows(InvalidEmailException.class, ()->{
            new Email("");
        });
    }

    @Test
    @DisplayName("이메일 형식이 유효함")
    void successEmail(){
        Email email = new Email("test@test.com");
        assertEquals(email.get(), "test@test.com");
    }

    @ParameterizedTest
    @DisplayName("여권 번호는 빈값을 허용하지만 빈값이 아닌 경우 유효해야함")
    @ValueSource(strings = {"invalid", "가나다", "Xz0382738"})
    void invalidPassport(String passport){
        assertThrows(InvalidPassportException.class, ()->{
           new Passport(passport);
        });
    }

    @Test
    @DisplayName("여권 번호는 빈값 허용")
    void emptyPassport(){
        Passport passport = new Passport("");
        assertEquals(passport, new Passport(""));
    }

    @Test
    void newUser(){
        PasswordEncoder passwordEncoder = createDelegatingPasswordEncoder();
        User user = User.builder()
                        .id(new UserId("ghdrlfehd"))
                        .email(new Email("test@test.com"))
                        .password(new Password("password", passwordEncoder))
                        .build();
        assertEquals(user.getId(), new UserId("ghdrlfehd"));
        assertTrue(passwordEncoder.matches("password", user.getPassword().get()));
    }

    @Test
    @DisplayName("해당 아이디를 가진 사용자가 이미 존재함")
    void alreadyExistUser(){
        UserRepository userCommandRepository = mock(UserRepository.class);
        User mockUser = UserFixture.aUser().build();
        mockUser.register(mock(RegisterUserValidator.class));
        when(userCommandRepository.findByUserId(new UserId("userid")))
                .thenReturn(Optional.of(mockUser));

        assertThrows(AlreadyExistUserException.class, ()->{
            new RegisterUserValidator(userCommandRepository, mock(PassportValidator.class)).validation(new UserId("userid"), null);
        });
    }

    @Test
    @DisplayName("사용자 생성시 여권번호를 입력했지만 여권번호가 유효하지 않음")
    void invalidPassportWhenRegister(){
        PassportRepository passportRepository = mock(PassportRepository.class);
        when(passportRepository.checkPassport("X10382738"))
                .thenReturn(false);

        assertThrows(InvalidPassportException.class, ()->{
            new RegisterUserValidator(mock(UserRepository.class), new PassportValidator(passportRepository)).validation(new UserId("userid"), new Passport("X10382738"));
        });
    }

    @Test
    @DisplayName("사용자 생성")
    void register(){
        User user = UserFixture.aUser().build();

        UserRepository userCommandRepository = mock(UserRepository.class);
        user.register(new RegisterUserValidator(userCommandRepository, mock(PassportValidator.class)));

        assertTrue(user.getState().isCreated());
        assertNotNull(user.getCreateDateTime());
    }

    @Test
    @DisplayName("비밀번호 변경")
    void changePassword(){
        PasswordEncoder passwordEncoder = createDelegatingPasswordEncoder();
        Password password = new Password("password", passwordEncoder);
        User user = UserFixture.aUser().password(password).build();

        user.changePassword("password", new Password("changePassword", passwordEncoder), passwordEncoder);

        assertTrue(passwordEncoder.matches("changePassword", user.getPassword().get()));
    }

    @Test
    @DisplayName("비밀번호 변경시 사용자가 CREATED 상태여야함")
    void alreadyDeletedUserWhenChangePassword(){
        User user = UserFixture.aDeletedUser();

        assertThrows(AlreadyDeletedUserException.class, ()->{
            user.changePassword("password", new Password("changePassword", mock(PasswordEncoder.class)), mock(PasswordEncoder.class));
        });
    }

    @Test
    @DisplayName("비밀번호 변경시 기존 비밀번호가 일치하지 않음")
    void notEqOriginPasswordWhenChangePassword(){
        PasswordEncoder passwordEncoder = createDelegatingPasswordEncoder();
        User user = UserFixture.aUser().password(new Password("password", passwordEncoder)).build();

        assertThrows(InvalidPasswordException.class, ()->{
            user.changePassword("notEqPassword", new Password("changePassword", passwordEncoder), passwordEncoder);
        });
    }

    @Test
    @DisplayName("여권 번호 변경")
    void changePassport() {
        PassportRepository passportRepository = mock(PassportRepository.class);
        when(passportRepository.checkPassport("X10382738"))
                .thenReturn(true);

        User user = UserFixture.aUser().build();
        user.changePassport(new PassportValidator(passportRepository), new Passport("X10382738"));
        assertEquals(user.getPassport(), new Passport("X10382738"));
    }

    @Test
    @DisplayName("여권 번호 변경시 사용자가 CREATED 상태여야함")
    void alreadyDeletedUserWhenChangePassport(){
        PassportRepository passportRepository = mock(PassportRepository.class);
        when(passportRepository.checkPassport("X10382738"))
                .thenReturn(true);

        User user = UserFixture.aDeletedUser();

        assertThrows(AlreadyDeletedUserException.class, ()->{
            user.changePassport(new PassportValidator(passportRepository), new Passport("X10382738"));
        });
    }

    @Test
    @DisplayName("여권 번호 변경시 여권번호가 유효하지 않음")
    void invalidPassportWhenChangePassport(){
        PassportRepository passportRepository = mock(PassportRepository.class);
        when(passportRepository.checkPassport("X10382738"))
                .thenReturn(false);

        User user = UserFixture.aUser().build();

        assertThrows(InvalidPassportException.class, ()->{
            user.changePassport(new PassportValidator(passportRepository), new Passport("X10382738"));
        });
    }

    @Test
    @DisplayName("회원 탈퇴시 기존 비밀번호가 일치하지 않음")
    void notEqOriginPasswordWhenWithdrawal(){
        PasswordEncoder passwordEncoder = createDelegatingPasswordEncoder();
        User user = UserFixture.aUser().password(new Password("password", passwordEncoder)).build();

        assertThrows(InvalidPasswordException.class, ()-> {
            user.withdrawal("notEqPassword", passwordEncoder);
        });
    }

    @Test
    @DisplayName("회원 탈퇴")
    void withdrawal(){
        PasswordEncoder passwordEncoder = createDelegatingPasswordEncoder();
        User user = UserFixture.aUser().password(new Password("password", passwordEncoder)).build();

        user.withdrawal("password", passwordEncoder);
        assertTrue(user.getState().isDeleted());
    }

    @Test
    @DisplayName("이미 회원탈퇴한 아이디")
    void alreadyDeletedUserWhenWithdrawal(){
        User user = UserFixture.aDeletedUser();

        assertThrows(AlreadyDeletedUserException.class, ()->{
            user.withdrawal("password", mock(PasswordEncoder.class));
        });
    }

    @Test
    @DisplayName("임시 비밀번호 발급")
    void temporaryPassword(){
        PasswordEncoder passwordEncoder = createDelegatingPasswordEncoder();
        User user = UserFixture.aUser().build();

        user.temporaryPassword(new Password("temporary", passwordEncoder));

        assertTrue(passwordEncoder.matches("temporary", user.getPassword().get()));
    }

    @Test
    @DisplayName("이미 탈퇴한 회원이 임시 비밀번호를 발급받을 수 없음")
    void alreadyDeletedUserWhenTemporaryPassword(){
        User user = UserFixture.aDeletedUser();

        assertThrows(AlreadyDeletedUserException.class, ()-> {
            user.temporaryPassword(mock(Password.class));
        });
    }

    @Test
    @DisplayName("입금")
    void deposit(){
        User user = UserFixture.aUser().build();
        user.deposit(30000L);
        assertEquals(30000L, user.getMoney().get());
    }

    @Test
    @DisplayName("이미 탈퇴한 아이디에 입금할 수 없음")
    void alreadyDeletedUserWhenDeposit(){
        User user = UserFixture.aDeletedUser();

        assertThrows(AlreadyDeletedUserException.class, ()->{
           user.deposit(3000L);
        });
    }

    @Test
    @DisplayName("지불")
    void pay(){
        User user = UserFixture.aUser().build();
        user.deposit(30000L);
        user.pay(3000L);
        assertEquals(27000L, user.getMoney().get());
    }

    @Test
    @DisplayName("이미 탈퇴한 아이디가 결제할 수 없음")
    void alreadyDeletedUserWhenPay(){
        User user = UserFixture.aDeletedUser();

        assertThrows(AlreadyDeletedUserException.class, ()->{
           user.pay(3000L);
        });
    }

}