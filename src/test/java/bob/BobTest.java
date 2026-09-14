package bob;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertTrue(greeting.contains("Morning! Bob here."));
    }

    @Test
    public void getResponse_validAndInvalidCommands_returnsAppropriateMessages() {
        Bob bob = new Bob();
        String response = bob.getResponse("list");
        assertNotNull(response);
        assertTrue(response.contains("job board"));

        String errorResponse = bob.getResponse("invalid command 123");
        assertNotNull(errorResponse);
        assertEquals("What's that?", errorResponse);
    }

    @Test
    public void getResponse_uppercaseCommand_parsesSuccessfully() {
        Bob bob = new Bob();

        String response = bob.getResponse("LIST");

        assertTrue(response.contains("job board"));
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
        assertTrue(response.contains("Tools down."));
        assertTrue(bob.isExit());
    }
}
