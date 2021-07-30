package user;

public class AlreadyDeletedUserException extends IllegalArgumentException {
    public AlreadyDeletedUserException(String msg) {
        super(msg);
    }
}
