package user;

public class InvalidPassportException extends IllegalArgumentException {
    public InvalidPassportException(String msg){
        super(msg);
    }
}
