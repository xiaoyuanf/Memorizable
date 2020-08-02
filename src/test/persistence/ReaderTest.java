package persistence;

import model.CardQueue;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {
    @Test
    public void testParseCardQueue() {
        try {
            CardQueue queue = Reader.readCardQueue(new File("./data/testQueue.txt"));
            assertEquals(queue.getSize(), 1);
            assertFalse(queue.isEmpty());
            assertEquals(queue.getNextCard().getAnswer(), "answer");
        } catch (IOException e) {
            fail("Shouldn't catch IOException");
        }
    }

    @Test
    void testIOException() {
        try {
            Reader.readCardQueue(new File("./path/does/not/exist/testAccount.txt"));
        } catch (IOException e) {
            // expected
        }
    }
}
