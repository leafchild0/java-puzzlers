package puzzlers.tasks;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Landing reservation system
 * <p>
 * Problem: Consider an airport with a single runway.
 * This airport receives landing requests from different airplanes.
 * A landing request contains the landing time (for example, 9:56) and the time in minutes
 * needed to complete the procedure (for example, 5 minutes). We denote it as 9:56 (5).
 * Write a snippet of code that uses a BST to design this reservation system.
 * Since there is a single runway, the code should reject any landing request
 * that overlaps an existing one. The order of requests dictates the order of reservations.
 * <p>
 * Solution: Let's consider a time screenshot of our landing timeline
 * (the order for the landing requests was
 * 10:10 (3), 10:14 (3), 9:55 (2), 10:18 (1), 9:58 (5), 9:47 (2), 9:41 (2), 10:22 (1), 9:50 (6), and 10:04 (4).
 * So, we have already done several reservations, as follows: at 9:41, an airplane will land and it will need
 * 2 minutes to complete the procedure; at 9:47 and 9:55, there are two other airplanes that need 2 minutes
 * to complete landing; at 9:58, we have an airplane that needs 5 minutes to complete landing; and so on.
 * Moreover, we also have two new landing requests denoted in the diagram as R1 and R2.
 * <p>
 * Notice that we cannot approve the R1 landing request.
 * The landing time is 9:50, and it needs 6 minutes to complete, so it ends at 9:56.
 * However, at 9:56, we already have the airplane from 9:55 on the runway.
 * Since we have a single runway, we reject this landing request.
 * We consider such cases as overlappings.
 * <p>
 * On the other hand, we approve the R2 landing request.
 * The request time is 10:04, and it needs 4 minutes to complete, so it ends at 10:08.
 * At 10:08, there is no other airplane on the runway since the next landing is at 10:10.
 * <p>
 * Can be solved via trees, sorted or unsorted array or list. Consider using array or linked list
 */
public class LandingSystem {

    private final List<LandingRequest> requests;

    public LandingSystem() {
        this.requests = new LinkedList<>();
    }

    public boolean addNewRequest(LandingRequest request) {
        if (canBeAdded(request)) {
            return requests.add(request);
        }

        return false;
    }

    private boolean canBeAdded(LandingRequest request) {
        // For all requests, check each elem and try to find overlaps
        // If not found, place node into needed position, otherwise - false
        for (LandingRequest r : requests) {
            long t1 = MINUTES.between(r.start, request.start);
            long t2 = MINUTES.between(r.start, request.start.plusMinutes(request.delay));

            if (t1 <= 0 && t2 >= 0) {
                System.out.println("Overlapping found at " + request.start);
                return false;
            }
        }
        return true;
    }

    public record LandingRequest(LocalTime start, int delay) {}
}
