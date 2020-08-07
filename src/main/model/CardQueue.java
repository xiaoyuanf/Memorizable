package model;

import com.google.gson.Gson;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

// Represents a queue of flashcards to be reviewed.
// The queue is ordered by the intervals of cards
public class CardQueue implements Saveable {
    public Queue<Card> myQueue;
    Comparator<Card> comparator = new CardComparator();
    public String queueName;

    // EFFECTS: initializes newly created CardQueue as an empty PriorityQueue and a queueName
    public CardQueue(String queueName) {
        myQueue = new PriorityQueue<Card>(10, comparator);
        this.queueName = queueName;
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

    public Card peekNextCard() {
        return myQueue.peek();
    }

    // EFFECTS: gets the number of cards currently in the queue
    public int getSize() {
        return myQueue.size();
    }

    // EFFECTS: gets the name of the queue
    public String getQueueName() {
        return this.queueName;
    }

    // EFFECTS: return true if the queue is empty, false otherwise
    public boolean isEmpty() {
        return (this.getSize() == 0);
    }

    // EFFECTS: write a cardqueue with queuename and cards, each in a line
    @Override
    public void save(PrintWriter printWriter) {
        Gson gson = new Gson();
        printWriter.print(queueName);
        printWriter.println();
        for (Card card : myQueue) {
            printWriter.print(gson.toJson(card));
            printWriter.println();
        }
    }
}
