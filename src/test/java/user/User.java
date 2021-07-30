package user;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class User {
    private UserId id;
    private Password password;
    private UserState state;
    private LocalDateTime createDateTime;

    public void register(RegisterUserValidator registerUserValidator) {
        registerUserValidator.validation(this.id);
        this.state = UserState.CREATED;
        this.createDateTime = LocalDateTime.now();
    }

    @Builder
    public User(UserId id, Password password) {
        verifyNotNullId(id);
        verifyNotNullPassword(password);
        this.id = id;
        this.password = password;
    }

    private void verifyNotNullId(UserId id) {
        if(Objects.isNull(id)){
            throw new InvalidUserIdException("id must not be null");
        }
    }

    private void verifyNotNullPassword(Password password) {
        if(Objects.isNull(password)){
            throw new InvalidPasswordException("password must not be null");
        }
    }
}
