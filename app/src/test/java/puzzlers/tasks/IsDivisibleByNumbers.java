package puzzlers.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsDivisibleByNumbers {

    @Test
    public void isDivisibleByNumbers() {
        Assertions.assertTrue(isDivisibleByNumbers(412));
        Assertions.assertFalse(isDivisibleByNumbers(143));
    }
}
