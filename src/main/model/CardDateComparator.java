package model;

import java.time.LocalDate;

import java.util.Comparator;

public class CardDateComparator implements Comparator<Card> {
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
