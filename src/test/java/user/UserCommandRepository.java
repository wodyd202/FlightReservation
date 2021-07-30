package user;

import java.util.Optional;

public interface UserCommandRepository {
    void save(User user);

    Optional<User> findByUserId(UserId id);
}
