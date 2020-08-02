package model;

import exception.EmptyQuestionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardComparatorTest {
    private CardComparator cardComparator;
    private Card testCard1, testCard2;

    @BeforeEach
    public void runBefore() {
        try {
            testCard1 = new Card("q1", "a1");
        } catch (EmptyQuestionException e) {
            e.printStackTrace();
        }

        try {
            testCard2 = new Card("q2", "a2");
        } catch (EmptyQuestionException e) {
            e.printStackTrace();
        }
        cardComparator = new CardComparator();
    }

    @Test
    public void testEqual() {
        assertEquals(cardComparator.compare(testCard1, testCard2), 0);
    }

    @Test
    public void testLessThan() {
        testCard2.setEasiness(true);
        testCard2.updateInterval();
        assertEquals(cardComparator.compare(testCard1, testCard2), -1);
    }

    @Test
    public void testGreaterThan() {
        testCard1.setEasiness(true);
        testCard1.updateInterval();
        assertEquals(cardComparator.compare(testCard1, testCard2), 1);
    }
}
