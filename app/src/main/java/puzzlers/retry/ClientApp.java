package puzzlers.retry;

public final class ClientApp {
    public static final String NOT_FOUND = "not found";
    private static Operation<String> op;
    
    public static void main(String[] args) throws Exception {
        noErrors();
        errorNoRetry();
        errorWithRetry();
        errorWithRetryExponentialBackoff();
    }

    private static void noErrors() throws Exception {
        op = new FindUserOperation("123");
        op.perform();
        System.out.println("No error operation");
    }

    private static void errorNoRetry() throws Exception {
        op = new FindUserOperation("123", new UserNotFoundException(NOT_FOUND));
        try {
            op.perform();
        } catch (UserNotFoundException e) {
            System.out.println("Error during user find with id 123");
        }
    }

    private static void errorWithRetry() throws Exception {
        final var retry = new Retry<>(
                new FindUserOperation("123", new UserNotFoundException(NOT_FOUND)),
                3,
                100, //100 ms delay between attempts
                e -> UserNotFoundException.class.isAssignableFrom(e.getClass())
        );
        op = retry;
        final var userId = op.perform();

        System.out.printf("However, retrying the operation while ignoring a recoverable error will eventually yield "
                        + "the result %s after a number of attempts %s%n", userId, retry.attempts()
        );
    }

    private static void errorWithRetryExponentialBackoff() throws Exception {
        final var retry = new RetryBackOff<>(
                new FindUserOperation("123", new UserNotFoundException(NOT_FOUND), new UserNotFoundException(NOT_FOUND), new UserNotFoundException(NOT_FOUND)),
                6,
                30000, //30s max delay between attempts
                e -> UserNotFoundException.class.isAssignableFrom(e.getClass())
        );
        op = retry;
        final var userId = op.perform();

        System.out.printf("However, retrying the operation while ignoring a recoverable error will eventually yield "
                        + "the result %s after a number of attempts %s%n", userId, retry.attempts()
        );
    }
}