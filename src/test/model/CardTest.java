package model;

import exception.EmptyQuestionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    Card testCard;
    String a = "answer";
    String q = "question";

    @BeforeEach
    public void setup() {
        try {
            testCard = new Card(q, a);
        } catch (EmptyQuestionException e) {
            fail("EmptyQuestionException should not have been thrown");
        }
    }

    @Test
    public void testCardConstructor() {
        String emptyQ = "";
        try {
            Card testEmptyCard = new Card(emptyQ, a);
            fail("Fail to catch EmptyQuestionException");
        } catch (EmptyQuestionException e){
            // as expected
        }
    }

    @Test
    public void testGetQuestion() {
        assertEquals(q, testCard.getQuestion());
        assertFalse(testCard.getQuestion().equals("q"));
    }

    @Test
    public void testGetAnswer() {
        assertEquals(a, testCard.getAnswer());
        assertFalse(testCard.getAnswer().equals("a"));
    }

    @Test
    public void testSetSchedule() {
        assertEquals(testCard.getNextViewDate(), LocalDate.now());
        testCard.updateInterval(true);
        testCard.setSchedule();
        assertEquals(testCard.getNextViewDate(), LocalDate.now().plusDays(2));

        testCard.updateInterval(true);
        testCard.setSchedule();
        assertEquals(testCard.getNextViewDate(), LocalDate.now().plusDays(4));

        testCard.updateInterval(false);
        testCard.setSchedule();
        assertEquals(testCard.getNextViewDate(), LocalDate.now());
    }

    @Test
    public void testEstimateInterval() {
        assertEquals(testCard.getInterval(), 0);
        assertEquals(testCard.estimateInterval(true), 2);
        assertEquals(testCard.estimateInterval(false), 0);

        testCard.updateInterval(false);
        assertEquals(testCard.getInterval(), 0);
        assertEquals(testCard.estimateInterval(true), 2);
        assertEquals(testCard.estimateInterval(false), 0);

        testCard.updateInterval(true);
        assertEquals(testCard.getInterval(), 2);
        assertEquals(testCard.estimateInterval(true), 4);
        assertEquals(testCard.estimateInterval(false), 0);

        testCard.updateInterval(true);
        assertEquals(testCard.getInterval(), 4);
        assertEquals(testCard.estimateInterval(true), 8);
        assertEquals(testCard.estimateInterval(false), 0);
    }


}