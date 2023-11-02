package puzzlers.retry;

/**
 * Performs any operation.
 *
 * @param <T> the return type
 */
public interface Operation<T> {
    /**
     * Performs some business operation, returning a value {@code T} if successful, otherwise throwing
     * an exception if an error occurs.
     *
     * @return the return value
     * @throws OperationException if the operation fails. Implementations are allowed to throw more
     *                           specific subtypes depending on the error conditions
     */
    T perform() throws OperationException;
}
