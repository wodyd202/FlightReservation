package user;

import com.ljy.flightreservation.user.domain.agg.User;
import com.ljy.flightreservation.user.domain.value.Password;
import com.ljy.flightreservation.user.domain.value.UserId;

import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

abstract  public class UserFixture {
    public static User.UserBuilder aUser(){
        return User.builder()
                    .id(new UserId("userid"))
                    .password(new Password("password", createDelegatingPasswordEncoder()));
    }
}
