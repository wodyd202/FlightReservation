package user;

import com.ljy.flightreservation.user.application.UserRepository;
import com.ljy.flightreservation.user.domain.agg.RegisterUserValidator;
import com.ljy.flightreservation.user.domain.agg.User;
import com.ljy.flightreservation.user.domain.model.ChangePasswordCommand;
import com.ljy.flightreservation.user.domain.model.RegisterUserCommand;
import com.ljy.flightreservation.user.domain.model.UserMapper;
import com.ljy.flightreservation.user.domain.model.UserModel;
import com.ljy.flightreservation.user.domain.value.UserId;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;
    private final RegisterUserValidator registerUserValidator;

    public UserService(UserRepository userRepo,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper,
                       RegisterUserValidator registerUserValidator) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.registerUserValidator = registerUserValidator;
    }

    public UserModel register(RegisterUserCommand userCommand) {
        User user = userMapper.mapFrom(userCommand);
        user.register(registerUserValidator);
        userRepo.save(user);
        return new UserModel(user);
    }

    public UserModel changePassword(ChangePasswordCommand userCommand,
                                    UserId userId) {
        User user = userRepo.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        user.changePassword(userCommand, passwordEncoder);
        userRepo.save(user);
        return new UserModel(user);
    }
}
