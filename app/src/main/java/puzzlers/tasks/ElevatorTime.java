package puzzlers.tasks;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class ElevatorTime {

    public static int timeForAllRequests(int k, int[] floors) {

        List<Integer> sorted = IntStream.of(floors)
                .boxed()
                .sorted(Collections.reverseOrder())
                .toList();

        int time = 0;
        for (int i = 0; i < sorted.size(); i += k) {
            time += (2 * sorted.get(i));
        }

        return time;

    }
}