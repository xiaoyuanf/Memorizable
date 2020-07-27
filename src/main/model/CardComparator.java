package model;

import java.util.Comparator;

// Provides an ordering for queue of Cards
public class CardComparator implements Comparator<Card> {
    @Override
    // EFFECTS: If the first card's interval is smaller than the second, returns -1,
    //          If the first card's interval is larger than the second, returns 1,
    //          If the two cards' intervals are the same, returns 0
    public int compare(Card o1, Card o2) {
        if (o1.getInterval() < o2.getInterval()) {
            return -1;
        }
        if (o1.getInterval() > o2.getInterval()) {
            return 1;
        }
        return 0;
    }
}
