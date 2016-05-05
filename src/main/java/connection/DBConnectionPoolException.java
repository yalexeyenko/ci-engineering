package connection;

public class DBConnectionPoolException extends RuntimeException {
    public DBConnectionPoolException() {
    }

    public DBConnectionPoolException(String message) {
        super(message);
    }

    public DBConnectionPoolException(String message, Exception exception) {
        super(message, exception);
    }
}
