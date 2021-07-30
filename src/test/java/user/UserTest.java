package user;

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
import static user.UserFixture.aUser;

public class UserTest {

    @ParameterizedTest
    @DisplayName("숫자 및 영문만 입력 가능")
    @ValueSource(strings = {"실패","  test", " test ", "test ", "ㄱㄴㄷㄹ"})
    void invalidEmail(String email) {
        assertThrows(InvalidUserIdException.class, ()->{
            new UserId(email);
        });
    }

    @Test
    @DisplayName("아이디는 비워져 있으면 안됨")
    void emptyEmail(){
        assertThrows(InvalidUserIdException.class, ()->{
           new UserId("");
        });
    }

    @Test
    @DisplayName("아이디 형식이 유효함")
    void successEmail(){
        UserId email = new UserId("test1234");
        assertEquals(email, new UserId("test1234"));
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

    @Test
    void newUser(){
        PasswordEncoder passwordEncoder = createDelegatingPasswordEncoder();
        User user = User.builder()
                        .id(new UserId("ghdrlfehd"))
                        .password(new Password("password", passwordEncoder))
                        .build();
        assertEquals(user.getId(), new UserId("ghdrlfehd"));
        assertTrue(passwordEncoder.matches("password", user.getPassword().get()));
    }

    @Test
    @DisplayName("해당 아이디를 가진 사용자가 이미 존재함")
    void alreadyExistUser(){
        UserCommandRepository userCommandRepository = mock(UserCommandRepository.class);
        User mockUser = aUser().build();
        mockUser.register(mock(RegisterUserValidator.class));
        when(userCommandRepository.findByUserId(new UserId("userid")))
                .thenReturn(Optional.of(mockUser));

        assertThrows(AlreadyExistUserException.class, ()->{
            new RegisterUserValidator(userCommandRepository).validation(new UserId("userid"));
        });
    }

    @Test
    @DisplayName("사용자 생성")
    void register(){
        User user = aUser().build();

        UserCommandRepository userCommandRepository = mock(UserCommandRepository.class);
        user.register(new RegisterUserValidator(userCommandRepository));

        assertTrue(user.getState().isCreated());
        assertNotNull(user.getCreateDateTime());
    }

}
