package puzzlers;

import org.junit.jupiter.api.Test;
import puzzlers.tasks.NextElemSameDigits;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class NextElemSameDigitsTest {

    @Test
    public void findNextGreater() {
        assertArrayEquals(new int[]{6}, NextElemSameDigits.findNextGreater(new int[]{6}));
        assertArrayEquals(new int[]{1, 2, 4, 3}, NextElemSameDigits.findNextGreater(new int[]{1, 2, 3, 4}));
        assertArrayEquals(new int[]{2, 1, 3, 2}, NextElemSameDigits.findNextGreater(new int[]{1, 2, 3, 2}));
        assertArrayEquals(new int[]{6, 2, 3, 1, 7, 8}, NextElemSameDigits.findNextGreater(new int[]{6, 2, 1, 8, 7, 3}));
    }
}
