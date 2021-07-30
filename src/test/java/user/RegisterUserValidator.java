package user;

import java.util.Optional;

public class RegisterUserValidator {
    private final UserCommandRepository userRepository;

    public RegisterUserValidator(UserCommandRepository userCommandRepository) {
        this.userRepository = userCommandRepository;
    }

    public void validation(UserId id) {
        Optional<User> findUser = userRepository.findByUserId(id);
        if(findUser.isPresent()){
            throw new AlreadyExistUserException("already exist id of user");
        }
    }
}
