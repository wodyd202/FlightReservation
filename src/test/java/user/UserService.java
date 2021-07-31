package user;

import com.ljy.flightreservation.user.application.UserRepository;
import com.ljy.flightreservation.user.domain.agg.PassportValidator;
import com.ljy.flightreservation.user.domain.agg.RegisterUserValidator;
import com.ljy.flightreservation.user.domain.agg.User;
import com.ljy.flightreservation.user.domain.model.ChangePassword;
import com.ljy.flightreservation.user.domain.model.RegisterUser;
import com.ljy.flightreservation.user.domain.model.UserMapper;
import com.ljy.flightreservation.user.domain.model.UserModel;
import com.ljy.flightreservation.user.domain.value.Passport;
import com.ljy.flightreservation.user.domain.value.Password;
import com.ljy.flightreservation.user.domain.value.UserId;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static user.UserServiceHelper.findByUserId;

public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;
    private final RegisterUserValidator registerUserValidator;
    private final PassportValidator passportValidator;

    public UserService(UserRepository userRepo,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper,
                       RegisterUserValidator registerUserValidator,
                       PassportValidator changePassportValidator) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.registerUserValidator = registerUserValidator;
        this.passportValidator = changePassportValidator;
    }

    public UserModel register(RegisterUser userCommand) {
        User user = userMapper.mapFrom(userCommand);
        user.register(registerUserValidator);
        userRepo.save(user);
        return new UserModel(user);
    }

    public UserModel changePassword(ChangePassword userCommand,
                                    UserId userId) {
        User user = findByUserId(userRepo, userId);
        user.changePassword(userCommand.getOriginPassword(),
                            new Password(userCommand.getChangePassword(), passwordEncoder),
                            passwordEncoder);
        userRepo.save(user);
        return new UserModel(user);
    }

    public UserModel changePassport(ChangePassport changePassportCommand,
                                    UserId userId) {
        User user = findByUserId(userRepo, userId);
        user.changePassport(passportValidator, new Passport(changePassportCommand.getChangePassport()));
        userRepo.save(user);
        return new UserModel(user);
    }

    public void withdrawal(Withdrawal withdrawalCommand, UserId userId) {
        User user = findByUserId(userRepo, userId);
        user.withdrawal(withdrawalCommand.getOriginPassword(), passwordEncoder);
        userRepo.save(user);
    }

    public void temporaryPassword(UserId userId) {
        User user = findByUserId(userRepo, userId);
        user.temporaryPassword(createTemporaryPassword());
        userRepo.save(user);
    }

    public void deposit(DepositMoney depositMoneyCommand, UserId userId) {
        User user = findByUserId(userRepo, userId);
        user.deposit(depositMoneyCommand.getMoney());
        userRepo.save(user);
    }

    private Password createTemporaryPassword() {
        return new Password(UUID.randomUUID().toString().substring(0,8), passwordEncoder);
    }
}
