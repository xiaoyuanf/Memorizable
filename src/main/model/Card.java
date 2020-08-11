package model;

import exception.EmptyQuestionException;

import java.time.LocalDate;

// represents a flashcard with content to memorize, difficulty, time of next visit
public class Card {

    private String question;        // content on the question side of the flashcard
    private String answer;          // content on the answer side of the flashcard
    private boolean isEasy;         // the content is correctly remembered or not
    // private boolean isNeverShow; // TO DO
    private int interval;       // days before the card is shown again
    private LocalDate initDate = LocalDate.now(); // date when the card is created
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
        this.isEasy = false;
        nextViewDate = initDate.plusDays(this.getInterval());
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    // MODIFIES: this
    // EFFECTS: set a card to be easy/difficult
    public void setEasiness(boolean easy) {
        isEasy = easy;
    }

    public boolean getEasiness() {
        return isEasy;
    }

    // EFFECTS: - If the original interval == 0, return easeFactor where:
    //	                            - easeFactor = 0 for items that are not correctly remembered
    //	                            - easeFactor = 2 for items that are correctly remembered.
    //          - If the original interval > 0, return interval * easeFactor
    private int computeInterval() {
        int easeFactor;
        if (isEasy) {
            easeFactor = 2;
        } else {
            easeFactor = 0;
        }
        if (interval == 0) {
            interval = easeFactor;
        } else {
            interval = easeFactor * interval;
        }
        return interval;
    }

    public void updateInterval() {
        interval = this.computeInterval();
    }

    public int getInterval() {
        return interval;
    }

    //MODIFIES: this
    //EFFECTS: set the nextViewDate to be current date + interval
    public void setSchedule() {
        nextViewDate = nextViewDate.plusDays(this.getInterval());
    }

    public LocalDate getNextViewDate() {
        return this.nextViewDate;
    }

}
