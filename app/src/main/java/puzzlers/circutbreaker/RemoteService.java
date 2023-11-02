package puzzlers.circutbreaker;

/**
 * The Remote service interface, used by {@link CircuitBreaker} for fetching response from remote
 * services.
 */
public interface RemoteService {

    String call() throws RemoteServiceException;
}