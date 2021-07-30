package user;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

abstract  public class UserFixture {
    public static User.UserBuilder aUser(){
        return User.builder()
                    .id(new UserId("userid"))
                    .password(new Password("password", createDelegatingPasswordEncoder()));
    }
}
