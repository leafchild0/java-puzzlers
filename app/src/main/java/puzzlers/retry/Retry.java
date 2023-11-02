package puzzlers.retry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * Decorates {@link Operation business operation} with "retry" capabilities.
 *
 * @param <T> the remote op's return type
 */
public final class Retry<T> implements Operation<T> {
    private final Operation<T> op;
    private final int maxAttempts;
    private final long delay;
    private final AtomicInteger attempts;
    private final Predicate<Exception> test;
    private final List<Exception> errors;

    /**
     * Ctor.
     *
     * @param op          the {@link Operation} to retry
     * @param maxAttempts number of times to retry
     * @param delay       delay (in milliseconds) between attempts
     * @param ignoreTests tests to check whether the remote exception can be ignored. No exceptions
     *                    will be ignored if no tests are given
     */
    @SafeVarargs
    public Retry(Operation<T> op, int maxAttempts, long delay, Predicate<Exception>... ignoreTests) {
        this.op = op;
        this.maxAttempts = maxAttempts;
        this.delay = delay;
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
                    Thread.sleep(this.delay);
                } catch (InterruptedException f) {
                    //ignore
                }
            }
        } while (this.attempts.incrementAndGet() < this.maxAttempts);

        return null;
    }
}