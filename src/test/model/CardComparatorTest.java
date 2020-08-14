package model;

import exception.EmptyQuestionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardComparatorTest {
    private CardDateComparator comparator;
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
        comparator = new CardDateComparator();
    }

    @Test
    public void testEqual() {
        assertEquals(comparator.compare(testCard1, testCard2), 0);
    }

    @Test
    public void testLessThan() {
        testCard2.updateInterval(true);
        testCard2.setSchedule();
        assertEquals(comparator.compare(testCard1, testCard2), -1);
    }

    @Test
    public void testGreaterThan() {
        testCard1.updateInterval(true);
        testCard1.setSchedule();
        assertEquals(comparator.compare(testCard1, testCard2), 1);
    }
}
