package puzzlers.circutbreaker;

/**
 * A quick response remote service, that responds healthy without any delay or failure.
 * Dummy one, just for the sake of demo
 */
public class QuickRemoteService implements RemoteService {

    @Override
    public String call() {
        return "Quick Service is working";
    }
}
