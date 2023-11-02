package puzzlers.retry;

/**
 * The top-most type in our exception hierarchy that signifies that an unexpected error condition
 * occurred.
 * Its use is reserved as a "catch-all" for cases where no other subtype captures the
 * specificity of the error condition in question.
 *
 */
public class OperationException extends Exception {
    public OperationException(String message) {
        super(message);
    }
}