package action;

public class ActionException extends RuntimeException {
    public ActionException(String message, Exception exception) {
        super(message, exception);
    }
}
