package model;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

// Represents a queue of flashcards to be reviewed.
// The queue is ordered by the intervals of cards
public class CardQueue {
    public Queue<Card> myQueue;
    Comparator<Card> comparator = new CardComparator();

    // EFFECTS: initializes newly created CardQueue as an empty PriorityQueue
    public CardQueue() {
        myQueue = new PriorityQueue<Card>(10, comparator);
    }

    // MODIFIES: this
    // EFFECTS: adds a card to the queue and returns true
    public boolean addCard(Card card) {
        return myQueue.add(card);
    }

    // MODIFIES: this
    // EFFECTS: Retrieves and removes the head of this queue, or returns null if this queue is empty.
    public Card getNextCard() {
        return myQueue.poll();
    }

    // EFFECTS: gets the number of cards currently in the queue
    public int getSize() {
        return myQueue.size();
    }

    // EFFECTS: return true if the queue is empty, false otherwise
    public boolean isEmpty() {
        if (myQueue.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

}
