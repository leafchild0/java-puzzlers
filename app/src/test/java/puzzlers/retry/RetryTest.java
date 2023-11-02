package puzzlers.retry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RetryTest {

    @Test
    public void noErrorOperation() throws OperationException {
        Operation<String> op = new FindUserOperation("123");
        assertEquals("123", op.perform());
    }

    @Test
    public void errorOperation() {
        Operation<String> op = new FindUserOperation("123", new UserNotFoundException());
        assertThrows(UserNotFoundException.class, op::perform);
    }

    @Test
    public void errorWithRetryOperation() throws OperationException {
        var retry = new Retry<>(new FindUserOperation("321", new UserNotFoundException()),
                3,
                100);
        assertThrows(UserNotFoundException.class, retry::perform);
        assertEquals("321", retry.perform());
        assertEquals(1, retry.attempts());
    }

    @Test
    public void errorWithRetryBackOffOperation() throws OperationException {
        var retry = new RetryBackOff<>(new FindUserOperation("321",
                new UserNotFoundException(),
                new DatabaseIsNotAvailableException("BOOM")),
                3,
                30000);
        assertThrows(UserNotFoundException.class, retry::perform);
        assertThrows(DatabaseIsNotAvailableException.class, retry::perform);
        assertEquals("321", retry.perform());
        assertEquals(2, retry.attempts());
    }
}
