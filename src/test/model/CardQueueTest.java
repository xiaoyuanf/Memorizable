package model;


import exception.EmptyQuestionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardQueueTest {
    CardQueue testQueue;
    String a = "answer";
    String q = "question";
    Card testCard1 = new Card(a, q);
    Card testCard2 = new Card(a, q);

    @BeforeEach
    public void setup() {
        testQueue = new CardQueue();
    }

    @Test
    public void testAddCard() {
        assertTrue(testQueue.isEmpty());
        testQueue.addCard(testCard1);
        assertFalse(testQueue.isEmpty());
        assertEquals(testQueue.getSize(), 1);
        testQueue.addCard(testCard2);
        assertEquals(testQueue.getSize(), 2);
    }

    @Test
    public void testGetNextCard() {
        assertEquals(testQueue.getNextCard(), null);
        testCard1.setDifficult();
        testCard2.setEasy();
        testQueue.addCard(testCard1);
        testQueue.addCard(testCard2);
        assertEquals(testQueue.getNextCard(), testCard1);
        assertEquals(testQueue.getSize(), 1);
    }

    @Test
    public void testGetNextCardWhenEqual() {
        testQueue.addCard(testCard1);
        testQueue.addCard(testCard2);
        testQueue.getNextCard();
        assertEquals(testQueue.getSize(), 1);
    }
}
