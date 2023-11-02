package puzzlers.retry;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy operation to test retry
 *
 * @param userId - id of user to find
 */
public record FindUserOperation(String userId, List<OperationException> errors) implements Operation<String> {
        public FindUserOperation(String userId, OperationException... errors) {
            this(userId, new ArrayList<>(List.of(errors)));
        }
        @Override
        public String perform() throws OperationException {
            if (!this.errors.isEmpty()) {
                throw this.errors.removeFirst();
            }

            return this.userId;
        }
    }