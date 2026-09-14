package bob;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import bob.storage.TaskStorage;

/**
 * Tests the behavior of {@link Bob}.
 */
public class BobTest {

    @TempDir
    private Path tempDir;

    @Test
    public void getGreeting_returnsWelcomeMessage() {
        Bob bob = new Bob();
        String greeting = bob.getGreeting();
        assertNotNull(greeting);
        assertTrue(greeting.contains("Morning! Bob here."));
    }

    @Test
    public void getGreeting_validAndInvalidStoredTasks_reportsLoadedTasksAndInvalidCount() throws IOException {
        Path testFile = tempDir.resolve("tasks.txt");
        Files.write(testFile, List.of("T | 0", "T | 1 | testing"));
        Bob bob = new Bob(new TaskStorage(testFile));

        String greeting = bob.getGreeting();

        assertEquals("Morning! Bob here. What's the next job?\n"
                + "Loaded tasks:\n1: [T][X] testing\n"
                + "Skipped invalid tasks: 1", greeting);
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
