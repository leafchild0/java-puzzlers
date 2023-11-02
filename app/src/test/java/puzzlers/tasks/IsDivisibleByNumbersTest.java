package puzzlers.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsDivisibleByNumbersTest {

    @Test
    public void isDivisibleByNumbers() {
        assertTrue(IsDivisibleByNumbers.isDivisibleByNumbers(412));
        assertFalse(IsDivisibleByNumbers.isDivisibleByNumbers(143));
    }
}
