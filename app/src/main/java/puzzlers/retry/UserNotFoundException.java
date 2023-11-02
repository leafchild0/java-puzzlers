package puzzlers.retry;

/**
 * Indicates that the user was not found.
 */
public class UserNotFoundException extends OperationException {

    public static final String NOT_FOUND = "User is not found";

    public UserNotFoundException() {
        super(NOT_FOUND);
    }
}
