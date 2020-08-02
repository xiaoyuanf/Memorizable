package persistence;

import com.google.gson.Gson;
import model.Card;
import model.CardQueue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// a reader that reads CardQueue data from a file
// attained and adapted from
// https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/master/src/main/ca/ubc/cpsc210/bank/persistence/Reader.java
public class Reader {

    // EFFECTS: returns a list of CardQueue parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static CardQueue readCardQueue(File file) throws IOException {
        ArrayList<String> lines = readFile(file);
        return parseContent(lines);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    // Use ArrayList to achieve O(1) of get(i)
    private static ArrayList<String> readFile(File file) throws IOException {
        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(file.toPath()));
        return lines;
    }

    // EFFECTS: returns a cardqueue parsed from list of strings
    // where each string contains data for one cardqueue
    private static CardQueue parseContent(ArrayList<String> lines) {
        CardQueue queue = new CardQueue(lines.get(0));

        for (int i = 1; i < lines.size(); i++) {
            queue.addCard(parseCardQueue(lines.get(i)));
        }
        return queue;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    /*private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }*/

    // REQUIRES: components has size 4 where element 0 represents the
    // id of the next account to be constructed, element 1 represents
    // the id, elements 2 represents the name and element 3 represents
    // the balance of the account to be constructed
    // EFFECTS: returns an account constructed from components
    private static Card parseCardQueue(String line) {
        Gson gson = new Gson();
        Card card = gson.fromJson(line, Card.class);
        return card;
    }


}