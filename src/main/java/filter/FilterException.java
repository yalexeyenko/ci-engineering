package filter;

public class FilterException extends RuntimeException {
    public FilterException() {
        super();
    }

    public FilterException(String message) {
        super(message);
    }

    public FilterException(String message, Exception exception) {
        super(message, exception);
    }
}
