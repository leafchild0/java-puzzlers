package puzzlers.tasks;

import java.util.LinkedList;
import java.util.List;

/**
 * Implement a simple rate limit with the following conditions:
 *  - There are number of requests for rate limit to handle
 *  - For each request only time is known
 *  - Rate limit should reject request if rate limit cannot accept it
 *  - Time for limit is one second
 */
public class RateLimit {

    public static final int TIMEOUT = 1000;
    private final List<Long> requests;
    private final int capacity;

    public RateLimit(int capacity) {
        this.capacity = capacity;
        this.requests = new LinkedList<>();
    }

    public boolean addNewRequest(long requestTime) {
        long curr = System.currentTimeMillis();

        // Cleanup data
        requests.removeIf(r -> r + TIMEOUT < curr);

        if (requests.size() < this.capacity) {
            return requests.add(requestTime);
        }
        return false;
    }
}
