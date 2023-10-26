package puzzlers.tasks;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class RateLimitVirtualThreads {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Semaphore SEMAPHORE = new Semaphore(20);
    public static final String RANDOM_WORD_URL = "https://horstmann.com/random/word";

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();
        List<Future<String>> futures = new ArrayList<>();
        int TASKS = 250;

        for (int i = 1; i <= TASKS; i++) futures.add(service.submit(() -> getData(RANDOM_WORD_URL)));
        for (Future<String> f : futures) System.out.println(f.get());

        service.close();
    }

    public static String getData(String url) {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            SEMAPHORE.acquire();

            try {
                Thread.sleep(100);
                return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            } finally {
                SEMAPHORE.release();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}