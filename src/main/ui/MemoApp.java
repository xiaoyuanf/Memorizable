package ui;

import model.Card;
import model.CardQueue;

import java.io.IOException;
import java.util.Scanner;

// Memorizable application
public class MemoApp {
    private CardQueue myQueue;
    private Scanner input;

    //EFFECTS: runs the Memorizable app
    public MemoApp() {
        runMemo();
    }

    // adopted from TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // MODIFIES: this
    // EFFECTS: processes user input
    private void runMemo() {
        boolean keepGoing = true;
        String mainCommand = null;
        input = new Scanner(System.in);

        while (keepGoing) {
            displayMainMenu();
            mainCommand = input.next();
            mainCommand = mainCommand.toLowerCase();

            if (mainCommand.equals("q")) {
                keepGoing = false;
            } else {
                processMainCommand(mainCommand);
            }
        }

        System.out.println("See you!");
    }

    // adopted from TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // EFFECTS: displays main menu of options to user
    private void displayMainMenu() {
        System.out.println("\n--Main Menu--");
        System.out.println("\nSelect from:");
        System.out.println("\tc -> create a new queue");
        // System.out.println("\tp -> pick an existing queue"); // TO DO
        System.out.println("\tq -> quit");
    }

    // adopted from TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // MODIFIES: this
    // EFFECTS: process user command on the main level
    private void processMainCommand(String mainCommand) {
        boolean keepGoing = true;
        String queueCommand = null;
        if (mainCommand.equals("c")) {
            myQueue = new CardQueue();
            System.out.println("\nYou have created a new queue");

            while (keepGoing) {
                displayQueueMenu();
                queueCommand = input.next();
                queueCommand = queueCommand.toLowerCase();

                if (queueCommand.equals("b")) {
                    keepGoing = false;
                } else {
                    processQueueCommand(queueCommand);
                }
            }
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // adopted from TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // EFFECTS: displays queue menu of options to user
    private void displayQueueMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a new card to the queue");
        System.out.println("\tr -> review cards in the queue");
        System.out.println("\tb -> back to main menu");
        // System.out.println("\tq -> quit"); //TO DO
    }

    // adopted from TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // MODIFIES: this
    // EFFECTS: process user command on a flashcard queue
    private void processQueueCommand(String queueCommand) {
        if (queueCommand.equals("a")) {
            addNewCard();
        } else if (queueCommand.equals("r")) {
            reviewCards();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: add a new flashcard with user input content to the queue
    private void addNewCard() {
        System.out.println("\nType the question...");
        String question = addContent();
        System.out.println("Type the answer...");
        String answer = addContent();

        Card card = new Card(question, answer);
        myQueue.addCard(card);
    }

    // adopted from TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // EFFECTS: prompts users to input content
    private String addContent() {
        String content = "";  // force entry into loop

        while (content.length() == 0) {
            System.out.println("content cannot be empty");
            content = input.nextLine();// get string with spaces
        }
        return content;
    }

    // MODIFIES: this
    // EFFECTS: review flashcards, update the easiness,
    //          and add the cards back to the queue with new intervals
    private void reviewCards() {
        for (int i = 0; i < 6; i++) {// TODO
            Card currCard = myQueue.getNextCard();
            System.out.println("\n" + currCard.getQuestion());
            System.out.println("Press Enter key to continue...");
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(currCard.getAnswer());

            updateEasiness(currCard);

            myQueue.addCard(currCard);
        }
    }

    // MODIFIES: this
    // EFFECTS: updateS the easiness and interval of a card given users' feedback
    private void updateEasiness(Card currCard) {
        String selectEasiness = "";

        while (!(selectEasiness.equals("y") || selectEasiness.equals("n"))) {
            System.out.println("\nWas this flashcard correctly remembered?");
            System.out.println("y/n");

            selectEasiness = input.next();
            selectEasiness = selectEasiness.toLowerCase();
        }

        if (selectEasiness.equals("y")) {
            currCard.setEasiness(true);
        }
        if (selectEasiness.equals("n")) {
            currCard.setEasiness(false);
        }

        currCard.updateInterval();
        System.out.println("You'll review the flashcard after " + currCard.getInterval() + " days.");
    }
}
