package puzzlers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsDivisibleByNumbers {

    @Test
    public void isDivisibleByNumbers() {
        assertTrue(puzzlers.tasks.IsDivisibleByNumbers.isDivisibleByNumbers(412));
        assertFalse(puzzlers.tasks.IsDivisibleByNumbers.isDivisibleByNumbers(143));
    }
}
