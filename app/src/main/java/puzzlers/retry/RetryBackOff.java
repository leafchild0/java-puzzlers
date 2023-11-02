package puzzlers.retry;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class RetryBackOff<T> implements Operation<T> {

    private static final Random RANDOM = new Random();
    private final Operation<T> op;
    private final int maxAttempts;
    private final long maxDelay;
    private final AtomicInteger attempts;
    private final Predicate<Exception> test;
    private final List<Exception> errors;

    @SafeVarargs
    public RetryBackOff(Operation<T> op, int maxAttempts, long maxDelay, Predicate<Exception>... ignoreTests) {
        this.op = op;
        this.maxAttempts = maxAttempts;
        this.maxDelay = maxDelay;
        this.attempts = new AtomicInteger();
        this.test = Arrays.stream(ignoreTests).reduce(Predicate::or).orElse(e -> false);
        this.errors = new ArrayList<>();
    }

    /**
     * The errors encountered while retrying, in the encounter order.
     *
     * @return the errors encountered while retrying
     */
    public List<Exception> errors() {
        return Collections.unmodifiableList(this.errors);
    }

    /**
     * The number of retries performed.
     *
     * @return the number of retries performed
     */
    public int attempts() {
        return this.attempts.intValue();
    }

    @Override
    public T perform() throws OperationException {
        do {
            try {
                return this.op.perform();
            } catch (OperationException e) {
                this.errors.add(e);

                if (this.attempts.incrementAndGet() >= this.maxAttempts || !this.test.test(e)) {
                    throw e;
                }

                try {
                    var testDelay = (long) Math.pow(2, this.attempts()) * 1000 + RANDOM.nextInt(1000);
                    var delay = Math.min(testDelay, this.maxDelay);
                    Thread.sleep(delay);
                } catch (InterruptedException f) {
                    //ignore
                }
            }
        } while (true);
    }
}
