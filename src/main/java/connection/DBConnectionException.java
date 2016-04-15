package connection;

public class DBConnectionException extends RuntimeException {
    public DBConnectionException(String message) {
    }

    public DBConnectionException(String message, Exception exception) {
    }
}
