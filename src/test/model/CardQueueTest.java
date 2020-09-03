package model;


import exception.EmptyQuestionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardQueueTest {
    CardQueue testQueue;
    String a = "answer";
    String q = "question";
    Card testCard1;

    {
        try {
            testCard1 = new Card(a, q);
        } catch (EmptyQuestionException e) {
            System.out.println(e.getMessage());
        }
    }

    Card testCard2;

    {
        try {
            testCard2 = new Card(a, q);
        } catch (EmptyQuestionException e) {
            System.out.println(e.getMessage());
        }
    }

    Card testCard3;

    {
        try {
            testCard3 = new Card(a, q);
        } catch (EmptyQuestionException e) {
            System.out.println(e.getMessage());
        }
    }

    @BeforeEach
    public void setup() {
        testQueue = new CardQueue("myTestQueue");
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
    public void testGetQueueName() {
        assertEquals(testQueue.getQueueName(), "myTestQueue");
    }

    @Test
    public void testGetNextCard() {
        assertEquals(testQueue.getNextCard(), null);
        testCard1.updateInterval(false);
        testCard2.updateInterval(true);
        testQueue.addCard(testCard1);
        testQueue.addCard(testCard2);
        testQueue.addCard(testCard2);
        testQueue.addCard(testCard2);
        assertEquals(testQueue.getNextCard(), testCard1);
        assertEquals(testQueue.getSize(), 3);
    }

    @Test
    public void testGetNextCardWhenEqual() {
        testQueue.addCard(testCard1);
        testQueue.addCard(testCard2);
        testQueue.getNextCard();
        assertEquals(testQueue.getSize(), 1);
    }

    @Test
    public void testPeekNextCard() {
        testQueue.addCard(testCard1);
        testQueue.addCard(testCard2);
        testQueue.updateQueue(true);

        testQueue.peekNextCard();
        assertEquals(testQueue.getSize(), 2);
    }

    @Test
    public void testUpdateQueue() {
        testQueue.addCard(testCard1);
        testQueue.addCard(testCard2);
        testQueue.addCard(testCard3);

        testQueue.updateQueue(true);

        testQueue.updateQueue(true);

        testQueue.updateQueue(false);

        assertEquals(testQueue.getNextCard().getInterval(), 0);
    }
}
