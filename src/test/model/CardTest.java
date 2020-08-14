package model;

import exception.EmptyQuestionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    public void testGetAnswer() {
        assertEquals(a, testCard.getAnswer());
    }

//    @Test
//    public void testSetEasiness() {
//        testCard.setEasiness(true);
//        assertTrue(testCard.getEasiness());
//        testCard.setEasiness(false);
//        assertFalse(testCard.getEasiness());
//    }


//    @Test
//    public void testSetIntervalEasy() {
//        testCard.setEasiness(true);
//        testCard.updateInterval();
//        assertEquals(testCard.getInterval(), 2);
//        testCard.setEasiness(true);
//        testCard.updateInterval();
//        assertEquals(testCard.getInterval(),4);
//        testCard.setEasiness(false);
//        testCard.updateInterval();
//        assertEquals(testCard.getInterval(), 0);
//    }
//
//    @Test
//    public void testSetIntervalDifficult() {
//        testCard.setEasiness(false);
//        testCard.updateInterval();
//        assertEquals(testCard.getInterval(), 0);
//        testCard.setEasiness(false);
//        testCard.updateInterval();
//        assertEquals(testCard.getInterval(), 0);
//        testCard.setEasiness(true);
//        testCard.updateInterval();
//        assertEquals(testCard.getInterval(), 2);
//    }

    @Test
    public void testSetSchedule() {

    }

    @Test
    public void testGetNextViewDate() {

    }

}