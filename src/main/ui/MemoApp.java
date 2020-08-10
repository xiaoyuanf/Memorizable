package ui;

import exception.EmptyQuestionException;
import model.Card;
import model.CardQueue;
import persistence.Reader;
import persistence.Writer;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

import static java.time.LocalDate.now;

// Memorizable application
public class MemoApp {
    private CardQueue myQueue;
    private Scanner input;
    private String myQueueName;
    private LocalDate curDate = now();

    //EFFECTS: runs the Memorizable app
    public MemoApp() {
        runMemo();
    }

    // adapted from TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // MODIFIES: this
    // EFFECTS: processes user input on the main menu level
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

    // adapted from TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // EFFECTS: displays main menu of options to user
    private void displayMainMenu() {
        System.out.println("\n--Main Menu--");
        System.out.println("\nSelect from:");
        System.out.println("\tc -> create a new queue");
        System.out.println("\tp -> pick an existing queue");
        System.out.println("\tq -> quit");
    }

    // adapted from TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // MODIFIES: this
    // EFFECTS: process user command on the main menu level
    private void processMainCommand(String mainCommand) {
        if (mainCommand.equals("c")) {
            createQueue();
            runQueueMenu();
        } else if (mainCommand.equals("p")) {
            loadQueue();
            runQueueMenu();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input on the queue menu level
    private void runQueueMenu() {
        boolean keepGoing = true;
        String queueCommand = null;
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
    }

    // MODIFIES: this
    // EFFECTS: create a new queue and save it
    private void createQueue() {
        System.out.println("\nGive the queue a name...");
        this.myQueueName = addContent();
        this.myQueue = new CardQueue(myQueueName);
        System.out.println("\nYou have created a new queue called " + myQueueName);
        saveQueue();
    }

    // adapted from TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // EFFECTS: save the state of queue to user-named file
    private void saveQueue() {
        String queueFile = "./data/" + this.myQueueName + ".txt";
        try {
            Writer writer = new Writer(new File(queueFile));
            writer.write(this.myQueue);
            writer.close();
            System.out.println("Queue saved to file " + queueFile);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save accounts to " + queueFile);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    // adapted from TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // MODIFIES: this
    // EFFECTS: list files in ./data/ and load the queue that user chooses
    //          if no queue exists, prompts user to create one
    private void loadQueue() {
        File dir = new File("./data/");
        // attained from https://stackoverflow.com/questions/15646358/how-to-list-only-non-hidden-and-non-system-file-in-jtree
        // excludes hidden files
        File[] files = dir.listFiles(file -> !file.isHidden());

        if (files.length == 0) {
            System.out.println("Please create a queue first!");
            runMemo();
        } else {
            System.out.println("Please pick a queue..");
            for (int i = 0; i < files.length; i++) {
                System.out.println("\t" + i + " -> " + files[i].getName());
            }
            try {
                int fileID = input.nextInt();
                String path = String.valueOf(files[fileID]);

                this.myQueue = Reader.readCardQueue(new File(path));
                this.myQueueName = path.substring(7, path.length() - 4);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // adapted from TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // EFFECTS: displays queue menu of options to user
    private void displayQueueMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a new card to the queue");
        System.out.println("\tr -> review cards in the queue");
        System.out.println("\tb -> back to main menu");
        // System.out.println("\tq -> quit"); //TODO
    }

    // adopted from TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // MODIFIES: this
    // EFFECTS: process user command on a flashcard queue
    private void processQueueCommand(String queueCommand) {
        if (queueCommand.equals("a")) {
            addNewCard();
            saveQueue();
        } else if (queueCommand.equals("r")) {
            reviewCards();
            saveQueue();
        } else if (queueCommand.equals("b")) {
            runMemo();
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

        Card card = null;
        try {
            card = new Card(question, answer);
        } catch (EmptyQuestionException e) {
            System.out.println(e.getMessage());
        }
        myQueue.addCard(card);
    }

    // adapted from TellerApp
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // EFFECTS: prompts users to input content
    private String addContent() {
        String content = "";  // force entry into loop
        //System.out.println("content cannot be empty");
        while (content.length() == 0) {
            content = input.nextLine();// get string with spaces
        }
        return content;
    }

    // MODIFIES: this
    // EFFECTS: review flashcards, update the easiness,
    //          and add the cards back to the queue with new intervals
    //          if no card exists, prompts users to add cards
    private void reviewCards() {
        if (myQueue.isEmpty()) {
            System.out.println("Please add some cards to the queue first!");
            runQueueMenu();
        } else {
            while (myQueue.peekNextCard().getNextViewDate().isEqual(curDate)) { // TODO let user decide how many to view
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
                currCard.setSchedule();

                myQueue.addCard(currCard);
            }
            System.out.println("All cards due today are reviewed!");
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the easiness and interval of a card given users' feedback
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
