package action;

public class ActionException extends RuntimeException {
    public ActionException() {
    }

    public ActionException(String message, Exception exception) {
        super(message, exception);
    }

    public ActionException(String message) {
        super(message);
    }
}
