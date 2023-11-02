package puzzlers.circutbreaker;

/**
 * Default super simple impl of the breaker
 */
public class DefaultCircuitBreaker implements CircuitBreaker {

    private final long retryTimePeriod;
    private final RemoteService service;
    private final int failureThreshold;

    // This is just a hardcode for example
    private final long futureTime = 1000L * 1000 * 1000 * 1000;
    private long lastFailureTime;
    private String lastFailureResponse;
    private int failureCount;
    private BreakerState state;

    /**
     * Constructor to create an instance of Circuit Breaker.
     *
     * @param timeout          Timeout for the API request. Not necessary for this simple example
     * @param failureThreshold Number of failures we receive from the depended on service before changing
     *                         state to 'OPEN'
     * @param retryTimePeriod  Time period after which a new request is made to remote service for
     *                         status check.
     */
    DefaultCircuitBreaker(RemoteService serviceToCall, long timeout, int failureThreshold,
                          long retryTimePeriod) {
        this.service = serviceToCall;
        // We start in a closed state hoping that everything is fine
        this.state = BreakerState.CLOSED;
        this.failureThreshold = failureThreshold;
        // Timeout for the API request.
        // Used to break the calls made to remote resource if it exceeds the limit
        this.retryTimePeriod = retryTimePeriod;
        // An absurd amount of time in future which basically indicates the last failure never happened
        this.lastFailureTime = System.nanoTime() + futureTime;
        this.failureCount = 0;
    }

    @Override
    public void recordSuccess() {
        this.failureCount = 0;
        this.lastFailureTime = System.nanoTime() + futureTime;
        this.state = BreakerState.CLOSED;
    }

    @Override
    public void recordFailure(String response) {
        failureCount = failureCount + 1;
        this.lastFailureTime = System.nanoTime();
        // Cache the failure response for returning on open state
        this.lastFailureResponse = response;
    }

    /**
     * Evaluate the current state based on failureThreshold, failureCount and lastFailureTime
     */
    protected void evaluateState() {
        if (failureCount >= failureThreshold) { //Then something is wrong with remote service
            if ((System.nanoTime() - lastFailureTime) > retryTimePeriod) {
                // We have waited long enough and should try checking if service is up
                state = BreakerState.HALF_OPEN;
            } else {
                // Service would still probably be down
                state = BreakerState.OPEN;
            }
        } else {
            // Everything is working fine
            state = BreakerState.CLOSED;
        }
    }

    @Override
    public String getState() {
        evaluateState();
        return state.name();
    }

    /**
     * Break the circuit beforehand if it is known service is down Or connect the circuit manually if
     * service comes online before expected.
     *
     * @param state State at which circuit is in
     */
    @Override
    public void setState(BreakerState state) {
        this.state = state;
        switch (state) {
            case OPEN -> {
                this.failureCount = failureThreshold;
                this.lastFailureTime = System.nanoTime();
            }
            case HALF_OPEN -> {
                this.failureCount = failureThreshold;
                this.lastFailureTime = System.nanoTime() - retryTimePeriod;
            }
            default -> this.failureCount = 0;
        }
    }

    /**
     * Executes service call.
     *
     * @return Value from the remote resource, stale response or a custom exception
     */
    @Override
    public String attemptRequest() throws RemoteServiceException {
        evaluateState();
        if (state == BreakerState.OPEN) {
            // return cached response if the circuit is in OPEN state
            return this.lastFailureResponse;
        } else {
            // Make the API request if the circuit is not OPEN
            try {
                //In a real application, this would be run in a thread and the timeout
                // parameter of the circuit breaker would be utilized to know if service
                // is working. Here, we simulate that based on server response itself
                var response = service.call();
                recordSuccess();
                return response;
            } catch (RemoteServiceException ex) {
                recordFailure(ex.getMessage());
                throw ex;
            }
        }
    }
}