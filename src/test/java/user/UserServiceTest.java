package user;

import com.ljy.flightreservation.user.application.UserRepository;
import com.ljy.flightreservation.user.domain.agg.PassportValidator;
import com.ljy.flightreservation.user.domain.agg.RegisterUserValidator;
import com.ljy.flightreservation.user.domain.agg.User;
import com.ljy.flightreservation.user.domain.model.ChangePassword;
import com.ljy.flightreservation.user.domain.model.RegisterUser;
import com.ljy.flightreservation.user.domain.model.UserMapper;
import com.ljy.flightreservation.user.domain.model.UserModel;
import com.ljy.flightreservation.user.domain.value.Password;
import com.ljy.flightreservation.user.domain.value.UserId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static user.UserFixture.aUser;

public class UserServiceTest {

    UserRepository userRepo = mock(UserRepository.class);
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    UserMapper userMapper = new UserMapper(passwordEncoder);

    UserService service = new UserService(userRepo, passwordEncoder, userMapper, mock(RegisterUserValidator.class), mock(PassportValidator.class));

    @Test
    @DisplayName("register user command >> user 매핑")
    void mapFrom() {
        RegisterUser userCommand = RegisterUser.builder()
                .id("test123")
                .password("password")
                .email("test@test.com")
                .build();
        UserMapper userMapper = new UserMapper(passwordEncoder);
        User user = userMapper.mapFrom(userCommand);
        assertEquals(user.getId(), new UserId("test123"));
        assertTrue(passwordEncoder.matches("password", user.getPassword().get()));
    }

    @Test
    @DisplayName("사용자 생성")
    void register() {
        RegisterUser userCommand = RegisterUser.builder()
                .id("test123")
                .password("passowrd")
                .email("test@test.com")
                .build();
        UserModel registerUser = service.register(userCommand);
        Assertions.assertNotNull(registerUser);
    }

    @Test
    @DisplayName("비밀번호 변경")
    void changePassword() {
        UserId userId = saveUser("test123", "originPassword");

        ChangePassword userCommand = ChangePassword.builder()
                .changePassword("changePassword")
                .originPassword("originPassword")
                .build();
        UserModel changePasswordUser = service.changePassword(userCommand, userId);
        assertTrue(passwordEncoder.matches("changePassword", changePasswordUser.getPassword()));
    }

    @Test
    @DisplayName("여권 번호 변경")
    void changePassport() {
        UserId userId = saveUser("test123", "originPassword");
        ChangePassport changePassportCommand = new ChangePassport("X10382738");
        UserModel userModel = service.changePassport(changePassportCommand, userId);

        assertEquals(userModel.getPassport(), "X10382738");
    }

    @Test
    @DisplayName("회원 탈퇴")
    void withdrawal() {
        UserId userId = saveUser("test123", "originPassword");
        Withdrawal withdrawalCommand = new Withdrawal("originPassword");

        assertDoesNotThrow(() -> {
            service.withdrawal(withdrawalCommand, userId);
        });
    }

    @Test
    @DisplayName("임시 비밀번호 발급")
    void temporaryPassword() {
        saveUser("test123", "originPassword");

        UserId userId = new UserId("test123");
        assertDoesNotThrow(()->{
            service.temporaryPassword(userId);
        });
    }

    @Test
    @DisplayName("잔액 충전")
    void deposit(){
        UserId userId = saveUser("test123", "originPassword");

        DepositMoney depositMoneyCommand = new DepositMoney(3000L);
        assertDoesNotThrow(()->{
            service.deposit(depositMoneyCommand, userId);
        });
    }

    private UserId saveUser(String userId, String password){
        User mockUser = aUser().password(new Password(password, passwordEncoder)).build();
        mockUser.register(mock(RegisterUserValidator.class));
        when(userRepo.findByUserId(new UserId(userId)))
                .thenReturn(Optional.of(mockUser));
        return new UserId(userId);
    }
}
