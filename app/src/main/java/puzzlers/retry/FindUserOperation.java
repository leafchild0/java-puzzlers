package puzzlers.retry;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Dummy operation to test retry
 *
 * @param userId - id of user to find
 * @param errors - error if any
 */
public record FindUserOperation(String userId, Deque<OperationException> errors) implements Operation<String> {
    public FindUserOperation(String userId, OperationException... errors) {
        this(userId, new ArrayDeque<>(List.of(errors)));
    }
    @Override
    public String perform() throws OperationException {
        if (!this.errors.isEmpty()) {
            throw this.errors.pop();
        }

        return this.userId;
    }
}