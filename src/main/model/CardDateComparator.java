package model;

import java.time.LocalDate;

import java.util.Comparator;

// Provides an ordering for queue of Cards
public class CardDateComparator implements Comparator<Card> {
    // EFFECTS: If the first card's nextViewDate is before than the second, returns -1,
    //          If the first card's nextViewDate is after than the second, returns 1,
    //          If the two cards' nextViewDates are the same, returns 0
    @Override
    public int compare(Card o1, Card o2) {
        LocalDate date1 = o1.getNextViewDate();
        LocalDate date2 = o2.getNextViewDate();

        if (date1.isBefore(date2)) {
            return -1;
        } else if (date2.isBefore(date1)) {
            return 1;
        } else {
            return 0;
        }
    }
}
