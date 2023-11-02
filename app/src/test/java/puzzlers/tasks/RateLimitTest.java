package puzzlers.tasks;

import org.junit.jupiter.api.Test;
import puzzlers.tasks.RateLimit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RateLimitTest {

    @Test
    public void addRequest() throws InterruptedException {
        RateLimit limit = new RateLimit(3);

        // Should accept 3
        assertTrue(limit.addNewRequest(System.currentTimeMillis()));
        assertTrue(limit.addNewRequest(System.currentTimeMillis()));
        assertTrue(limit.addNewRequest(System.currentTimeMillis()));
        assertFalse(limit.addNewRequest(System.currentTimeMillis()));

        Thread.sleep(1100);

        // And can be added again after 1 second+
        assertTrue(limit.addNewRequest(System.currentTimeMillis()));
    }
}
