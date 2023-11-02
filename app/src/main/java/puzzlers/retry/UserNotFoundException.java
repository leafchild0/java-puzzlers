package puzzlers.retry;

/**
 * Indicates that the user was not found.
 */
public class UserNotFoundException extends OperationException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
