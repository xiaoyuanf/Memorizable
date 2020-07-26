package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    Card testCard;
    String a = "answer";
    String q = "question";

    @BeforeEach
    public void setup() {
        testCard = new Card(q, a);
    }

    @Test
    public void testGetQuestion() {
        assertEquals(q, testCard.getQuestion());
    }

    @Test
    public void testGetAnswer() {
        assertEquals(a, testCard.getAnswer());
    }

    @Test
    public void testSetEasy() {
        testCard.setEasy();
        assertTrue(testCard.getEasiness());
    }

    @Test
    public void testSetDifficult() {
        testCard.setDifficult();
        assertFalse(testCard.getEasiness());
    }

    @Test
    public void testSetIntervalEasy() {
        testCard.setEasy();
        testCard.setInterval();
        assertEquals(testCard.getInterval(), 2);
        testCard.setEasy();
        testCard.setInterval();
        assertEquals(testCard.getInterval(),4);
        testCard.setDifficult();
        testCard.setInterval();
        assertEquals(testCard.getInterval(), 0);
    }

    @Test
    public void testSetIntervalDifficult() {
        testCard.setDifficult();
        testCard.setInterval();
        assertEquals(testCard.getInterval(), 0);
        testCard.setDifficult();
        testCard.setInterval();
        assertEquals(testCard.getInterval(), 0);
        testCard.setEasy();
        testCard.setInterval();
        assertEquals(testCard.getInterval(), 2);
    }

}