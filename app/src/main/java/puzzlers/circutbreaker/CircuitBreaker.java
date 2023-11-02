package puzzlers.circutbreaker;

/**
 * The Circuit breaker interface.
 */
public interface CircuitBreaker {

    void recordSuccess();

    void recordFailure(String response);

    String getState();

    void setState(BreakerState state);

    String attemptRequest() throws RemoteServiceException;
}