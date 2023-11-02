package puzzlers.circutbreaker;

/**
 * Client app to showcase the breaker
 * Creates few services, create monitoring and performs all the calls to see breaker in action
 */
public class ClientApp {

    public static void main(String[] args) {

        var serverStartTime = System.nanoTime();

        var delayedService = new DelayedRemoteService(serverStartTime, 5);
        var delayedServiceCB = new DefaultCircuitBreaker(delayedService, 3000, 2,
                2000 * 1000 * 1000);

        var quickService = new QuickRemoteService();
        var quickServiceCB = new DefaultCircuitBreaker(quickService, 3000, 2,
                2000 * 1000 * 1000);

        // Create an object of monitoring service which makes both local and remote calls
        var monitoringService = new MonitoringService(delayedServiceCB, quickServiceCB);

        // Fetch response from local resource
        System.out.println(monitoringService.localResourceResponse());

        // Fetch response from delayed service 2 times, to meet the failure threshold
        System.out.println(monitoringService.performDelayedServiceRequest());
        System.out.println(monitoringService.performDelayedServiceRequest());

        //Fetch current state of delayed service circuit breaker after crossing failure threshold limit
        // Should be OPEN state
        System.out.println(delayedServiceCB.getState());

        // Meanwhile, the delayed service is down, fetch response from the healthy quick service
        System.out.println(monitoringService.performQuickServiceRequest());
        System.out.println(quickServiceCB.getState());

        //Wait for the delayed service to become responsive
        try {
            System.out.println("Waiting for delayed service to become responsive");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Check the state of delayed circuit breaker, should be HALF_OPEN
        System.out.println(delayedServiceCB.getState());

        //Fetch response from delayed service, which should be healthy by now
        System.out.println(monitoringService.performDelayedServiceRequest());
        //As successful response is fetched, it should be CLOSED again.
        System.out.println(delayedServiceCB.getState());
    }
}
