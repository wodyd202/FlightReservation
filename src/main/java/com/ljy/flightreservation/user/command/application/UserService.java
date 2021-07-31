package com.ljy.flightreservation.user.command.application;

import com.ljy.flightreservation.user.command.application.model.*;
import com.ljy.flightreservation.user.command.domain.agg.PassportValidator;
import com.ljy.flightreservation.user.command.domain.agg.RegisterUserValidator;
import com.ljy.flightreservation.user.command.domain.agg.User;
import com.ljy.flightreservation.user.command.domain.value.Passport;
import com.ljy.flightreservation.user.command.domain.value.Password;
import com.ljy.flightreservation.user.command.domain.value.UserId;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
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
        User user = UserServiceHelper.findByUserId(userRepo, userId);
        user.changePassword(userCommand.getOriginPassword(),
                            new Password(userCommand.getChangePassword(), passwordEncoder),
                            passwordEncoder);
        userRepo.save(user);
        return new UserModel(user);
    }

    public UserModel changePassport(ChangePassport changePassportCommand,
                                    UserId userId) {
        User user = UserServiceHelper.findByUserId(userRepo, userId);
        user.changePassport(passportValidator, new Passport(changePassportCommand.getChangePassport()));
        userRepo.save(user);
        return new UserModel(user);
    }

    public void withdrawal(Withdrawal withdrawalCommand, UserId userId) {
        User user = UserServiceHelper.findByUserId(userRepo, userId);
        user.withdrawal(withdrawalCommand.getOriginPassword(), passwordEncoder);
        userRepo.save(user);
    }

    public void temporaryPassword(UserId userId) {
        User user = UserServiceHelper.findByUserId(userRepo, userId);
        user.temporaryPassword(createTemporaryPassword());
        userRepo.save(user);
    }

    public void deposit(DepositMoney depositMoneyCommand, UserId userId) {
        User user = UserServiceHelper.findByUserId(userRepo, userId);
        user.deposit(depositMoneyCommand.getMoney());
        userRepo.save(user);
    }

    private Password createTemporaryPassword() {
        return new Password(UUID.randomUUID().toString().substring(0,8), passwordEncoder);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserId(new UserId(username)).orElseThrow(()->new UsernameNotFoundException(username));
        return new org.springframework.security.core.userdetails.User(username, user.getPassword().get(), Arrays.asList(new SimpleGrantedAuthority("ROLE" + user.getRole())));
    }
}
