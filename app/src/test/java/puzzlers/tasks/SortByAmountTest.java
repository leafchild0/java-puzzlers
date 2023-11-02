package puzzlers.tasks;

import org.junit.jupiter.api.Test;
import puzzlers.tasks.SortByAmount;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class SortByAmountTest {

    @Test
    public void sortByAccurence() {
        assertIterableEquals(Arrays.asList("BB", "AA", "CC", "DD"), SortByAmount.sortByOccurrence("AA BB CC DD BB"));
        assertIterableEquals(Arrays.asList("AA", "BB", "CC", "DD"), SortByAmount.sortByOccurrence("AA BB CC DD BB AA AA AA"));
    }
}
