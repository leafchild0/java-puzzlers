package puzzlers;

import org.junit.jupiter.api.Test;
import puzzlers.tasks.ElevatorTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorTimeTest {

    @Test
    public void timeForAllRequests() {
        assertEquals(12, ElevatorTime.timeForAllRequests(3, new int[]{4, 2, 1, 2, 4}));
        assertEquals(14, ElevatorTime.timeForAllRequests(2, new int[]{4, 2, 1, 2, 4}));

    }
}
