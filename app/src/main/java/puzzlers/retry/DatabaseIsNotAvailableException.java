package puzzlers.retry;

/**
 * This is fatal exception to show something retry can handle
 */
public class DatabaseIsNotAvailableException extends OperationException {
    public DatabaseIsNotAvailableException(String message) {
        super(message);
    }
}
