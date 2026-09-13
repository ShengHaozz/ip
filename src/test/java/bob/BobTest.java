package bob;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the behavior of {@link Bob}.
 */
public class BobTest {

    @Test
    public void getGreeting_returnsWelcomeMessage() {
        Bob bob = new Bob();
        String greeting = bob.getGreeting();
        assertNotNull(greeting);
        assertTrue(greeting.contains("Hello! I'm Bob."));
    }

    @Test
    public void getResponse_validAndInvalidCommands_returnsAppropriateMessages() {
        Bob bob = new Bob();
        String response = bob.getResponse("list");
        assertNotNull(response);
        assertTrue(response.contains("Tasks:"));

        String errorResponse = bob.getResponse("invalid command 123");
        assertNotNull(errorResponse);
        assertTrue(errorResponse.contains("What's that?"));
    }

    @Test
    public void getResponse_invalidCommand_setsErrorState() {
        Bob bob = new Bob();

        bob.getResponse("invalid command 123");

        assertTrue(bob.isLastResponseError());
    }

    @Test
    public void getResponse_validCommandAfterError_clearsErrorState() {
        Bob bob = new Bob();
        bob.getResponse("invalid command 123");

        bob.getResponse("list");

        assertFalse(bob.isLastResponseError());
    }

    @Test
    public void getResponse_byeCommand_setsIsExitTrue() {
        Bob bob = new Bob();
        assertFalse(bob.isExit());

        String response = bob.getResponse("bye");
        assertNotNull(response);
        assertTrue(response.contains("Goodbye."));
        assertTrue(bob.isExit());
    }
}
