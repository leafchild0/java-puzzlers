package puzzlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puzzlers.tasks.LandingSystem;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LandingSystemTest {

    LandingSystem system = new LandingSystem();

    @BeforeEach
    public void setUp() {
        system.addNewRequest(new LandingSystem.LandingRequest(LocalTime.of(10, 10), 3));
        system.addNewRequest(new LandingSystem.LandingRequest(LocalTime.of(10, 14), 3));
        system.addNewRequest(new LandingSystem.LandingRequest(LocalTime.of(9, 55), 2));
        system.addNewRequest(new LandingSystem.LandingRequest(LocalTime.of(10, 18), 1));
        system.addNewRequest(new LandingSystem.LandingRequest(LocalTime.of(9, 58), 5));
        system.addNewRequest(new LandingSystem.LandingRequest(LocalTime.of(9, 47), 2));
        system.addNewRequest(new LandingSystem.LandingRequest(LocalTime.of(9, 41), 2));
        system.addNewRequest(new LandingSystem.LandingRequest(LocalTime.of(10, 22), 1));
    }

    @Test
    public void addNewRequest() {
        assertFalse(system.addNewRequest(new LandingSystem.LandingRequest(LocalTime.of(9, 50), 6)));
        assertTrue(system.addNewRequest(new LandingSystem.LandingRequest(LocalTime.of(10, 4), 4)));
    }
}
