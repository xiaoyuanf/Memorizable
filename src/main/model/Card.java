package model;

// represents a flashcard with content to memorize, difficulty, time of next visit
public class Card {

    private String question;
    private String answer;
    private boolean isEasy;
    // private boolean isNeverShow;
    private int interval = 0;

    // EFFECTS: create a card with given question and answer
    // REQUIRES: the question string is not empty
    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    // EFFECTS: set a card to be easy
    // MODIFIES: this
    public void setEasy() {
        isEasy = true;
    }

    // EFFECTS: set a card to be difficult
    // MODIFIES: this
    public void setDifficult() {
        isEasy = false;
    }

    private int computeInterval() {
        return 0; // stub
    }

    public int getInterval() {
        return computeInterval();
    }



}
