package model;

import exception.EmptyQuestionException;

import java.time.LocalDate;

// represents a flashcard with content to memorize, difficulty, time of next visit
public class Card {

    private String question;        // content on the question side of the flashcard
    private String answer;          // content on the answer side of the flashcard
    private int interval;       // days before the card is shown again
    private LocalDate nextViewDate;  // date when the card will be reviewed again

    // REQUIRES: the question string is not empty
    // EFFECTS: create a card with given question and answer
    public Card(final String question, String answer) throws EmptyQuestionException {
        if (question.length() == 0) {
            throw new EmptyQuestionException("Question should not be empty");
        }
        this.question = question;
        this.answer = answer;
        this.interval = 0;
        nextViewDate = LocalDate.now();
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    // EFFECTS: - If the original interval == 0, return easeFactor where:
    //	                            - easeFactor = 0 for items that are not correctly remembered
    //	                            - easeFactor = 2 for items that are correctly remembered.
    //          - If the original interval > 0, return interval * easeFactor
    public void updateInterval(boolean easy) {
        int easeFactor;
        if (easy) {
            easeFactor = 2;
        } else {
            easeFactor = 0;
        }
        if (this.interval == 0) {
            this.interval = easeFactor;
        } else {
            this.interval = easeFactor * interval;
        }
    }

    public int getInterval() {
        return interval;
    }

    //EFFECTS: calculates the interval based on user feedback of easy/hard
    public int estimateInterval(boolean easy) {
        int estInterval;
        int easeFactor;
        if (easy) {
            easeFactor = 2;
        } else {
            easeFactor = 0;
        }
        if (this.interval == 0) {
            estInterval = easeFactor;
        } else {
            estInterval = easeFactor * interval;
        }
        return estInterval;
    }

    //MODIFIES: this
    //EFFECTS: set the nextViewDate to be current date + interval
    public void setSchedule() {
        nextViewDate = LocalDate.now().plusDays(this.getInterval());
    }

    public LocalDate getNextViewDate() {
        return this.nextViewDate;
    }

}
