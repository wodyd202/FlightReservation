package user;

import com.ljy.flightreservation.user.application.UserRepository;
import com.ljy.flightreservation.user.domain.agg.User;
import com.ljy.flightreservation.user.domain.value.UserId;

public class UserServiceHelper {
    public static User findByUserId(UserRepository userRepo, UserId userId) {
        return userRepo.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
    }
}
