package com.ljy.flightreservation.user.command.application;

import com.ljy.flightreservation.user.command.application.event.*;
import com.ljy.flightreservation.user.command.application.model.*;
import com.ljy.flightreservation.user.command.domain.agg.PassportValidator;
import com.ljy.flightreservation.user.command.domain.agg.RegisterUserValidator;
import com.ljy.flightreservation.user.command.domain.agg.User;
import com.ljy.flightreservation.user.command.domain.value.Passport;
import com.ljy.flightreservation.user.command.domain.value.Password;
import com.ljy.flightreservation.user.command.domain.value.UserId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserService {
    private final ApplicationEventPublisher publisher;

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;
    private final RegisterUserValidator registerUserValidator;
    private final PassportValidator passportValidator;

    public UserService(ApplicationEventPublisher publisher,
                       UserRepository userRepo,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper,
                       RegisterUserValidator registerUserValidator,
                       PassportValidator changePassportValidator) {
        this.publisher = publisher;
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
        UserModel userModel = new UserModel(user);
        publisher.publishEvent(RegisteredUserEvent.builder()
                                                    .id(userModel.getId())
                                                    .passport(userModel.getPassport())
                                                    .password(userModel.getPassword())
                                                    .money(userModel.getMoney())
                                                    .createDateTime(userModel.getCreateDateTime())
                                                    .userState(userModel.getUserState())
                                                    .role(userModel.getRole())
                                                    .build());
        return userModel;
    }

    public UserModel changePassword(ChangePassword userCommand,
                                    UserId userId) {
        User user = UserServiceHelper.findByUserId(userRepo, userId);
        user.changePassword(userCommand.getOriginPassword(),
                            new Password(userCommand.getChangePassword(), passwordEncoder),
                            passwordEncoder);
        userRepo.save(user);
        UserModel userModel = new UserModel(user);
        publisher.publishEvent(ChangedPasswordEvent.builder()
                                                  .id(userModel.getId())
                                                  .password(userModel.getPassword()).build());
        return userModel;
    }

    public UserModel changePassport(ChangePassport changePassportCommand,
                                    UserId userId) {
        User user = UserServiceHelper.findByUserId(userRepo, userId);
        user.changePassport(passportValidator, new Passport(changePassportCommand.getChangePassport()));
        userRepo.save(user);
        UserModel userModel = new UserModel(user);
        publisher.publishEvent(ChangedPassportEvent.builder()
                                                    .id(userModel.getId())
                                                    .passport(userModel.getPassport())
                                                    .build());
        return userModel;
    }

    public void withdrawal(Withdrawal withdrawalCommand, UserId userId) {
        User user = UserServiceHelper.findByUserId(userRepo, userId);
        user.withdrawal(withdrawalCommand.getOriginPassword(), passwordEncoder);
        userRepo.save(user);
        publisher.publishEvent(new WithdrawaledEvent(userId.get()));
    }

    public void temporaryPassword(UserId userId) {
        User user = UserServiceHelper.findByUserId(userRepo, userId);
        user.temporaryPassword(createTemporaryPassword());
        userRepo.save(user);
        publisher.publishEvent(TemporaryedPasswordEvent.builder()
                                                        .id(userId.get())
                                                        .password(user.getPassword().get())
                                                        .build());
    }

    public void deposit(DepositMoney depositMoneyCommand, UserId userId) {
        User user = UserServiceHelper.findByUserId(userRepo, userId);
        user.deposit(depositMoneyCommand.getMoney());
        userRepo.save(user);
        publisher.publishEvent(DepositedMoneyEvent.builder()
                                                    .id(userId.get())
                                                    .money(user.getMoney().get())
                                                    .build());
    }

    private Password createTemporaryPassword() {
        return new Password(UUID.randomUUID().toString().substring(0,8), passwordEncoder);
    }
}
