package user;

import com.ljy.flightreservation.user.application.UserRepository;
import com.ljy.flightreservation.user.domain.agg.RegisterUserValidator;
import com.ljy.flightreservation.user.domain.agg.User;
import com.ljy.flightreservation.user.domain.model.ChangePasswordCommand;
import com.ljy.flightreservation.user.domain.model.RegisterUserCommand;
import com.ljy.flightreservation.user.domain.model.UserMapper;
import com.ljy.flightreservation.user.domain.model.UserModel;
import com.ljy.flightreservation.user.domain.value.Password;
import com.ljy.flightreservation.user.domain.value.UserId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;
import static user.UserFixture.aUser;

public class UserServiceTest {

    PasswordEncoder passwordEncoder = createDelegatingPasswordEncoder();

    @Test
    @DisplayName("register user command >> user 매핑")
    void mapFrom(){
        RegisterUserCommand userCommand = RegisterUserCommand.builder()
                .id("test123")
                .password("passowrd")
                .build();
        UserMapper userMapper = new UserMapper(passwordEncoder);
        User user = userMapper.mapFrom(userCommand);
        assertEquals(user.getId(), new UserId("test123"));
        assertTrue(passwordEncoder.matches("password", user.getPassword().get()));
    }

    @Test
    @DisplayName("사용자 생성")
    void register() {
        UserService service = new UserService(mock(UserRepository.class), passwordEncoder, new UserMapper(passwordEncoder), mock(RegisterUserValidator.class));

        RegisterUserCommand userCommand = RegisterUserCommand.builder()
                                                             .id("test123")
                                                             .password("passowrd")
                                                             .build();
        UserModel registerUser = service.register(userCommand);
        Assertions.assertNotNull(registerUser);
    }

    @Test
    @DisplayName("비밀번호 변경")
    void changePassword(){
        UserRepository userRepository = mock(UserRepository.class);
        User mockUser = aUser().password(new Password("originPassword", passwordEncoder)).build();
        mockUser.register(mock(RegisterUserValidator.class));
        when(userRepository.findByUserId(new UserId("test123")))
                        .thenReturn(Optional.of(mockUser));

        UserService service = new UserService(userRepository, passwordEncoder, new UserMapper(passwordEncoder), mock(RegisterUserValidator.class));

        ChangePasswordCommand userCommand = ChangePasswordCommand.builder()
                                                                 .changePassword("changePassword")
                                                                 .originPassword("originPassword")
                                                                 .build();
        UserId userId = new UserId("test123");
        UserModel changePasswordUser = service.changePassword(userCommand, userId);
        assertTrue(passwordEncoder.matches("changePassword", changePasswordUser.getPassword()));
    }
}
