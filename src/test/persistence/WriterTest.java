package persistence;

import com.sun.scenario.effect.impl.prism.PrImage;
import exception.EmptyQuestionException;
import model.Card;
import model.CardQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {
    private static final String QUEUE_NAME = "testQueue";
    private static final String TEST_FILE = "./data/" + QUEUE_NAME + ".txt";
    private Writer testWriter;
    private CardQueue testCardQueue;

    @BeforeEach
    public void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        testCardQueue = new CardQueue(QUEUE_NAME);
        try {
            testCardQueue.addCard(new Card("question", "answer"));
        } catch (EmptyQuestionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWriteCardQueue() {
        testWriter.write(testCardQueue);
        testWriter.close();

        try {
            CardQueue queue = Reader.readCardQueue(new File(TEST_FILE));
            assertEquals(queue.getQueueName(), QUEUE_NAME);
            assertEquals(queue.getSize(), 1);
            assertFalse(queue.isEmpty());
            assertEquals(queue.getNextCard().getQuestion(), "question");
        } catch (IOException e) {
            fail("Shouldn't catch IOException");
        }

    }
}


