package model;

// represents a flashcard with content to memorize, difficulty, time of next visit
public class Card {

    private String question;        // content on the question side of the flashcard
    private String answer;          // content on the answer side of the flashcard
    private boolean isEasy;         // the content is correctly remembered or not
    // private boolean isNeverShow; // TO DO
    private int interval;       // days before the card is shown again

    // REQUIRES: the question string is not empty
    // EFFECTS: create a card with given question and answer
    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.interval = 0;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    // MODIFIES: this
    // EFFECTS: set a card to be easy
    public void setEasy() {
        isEasy = true;
    }

    // MODIFIES: this
    // EFFECTS: set a card to be difficult
    public void setDifficult() {
        isEasy = false;
    }

    public boolean getEasiness() {
        return isEasy;
    }

    // REQUIRES: interval >= 0
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

    public void setInterval() {
        interval = this.computeInterval();
    }

    public int getInterval() {
        return interval;
    }




}
