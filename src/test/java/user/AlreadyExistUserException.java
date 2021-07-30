package user;

public class AlreadyExistUserException extends IllegalArgumentException {
    public AlreadyExistUserException(String msg) {
        super(msg);
    }
}
