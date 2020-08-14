package ui.gui;

import model.CardQueue;
import persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

//  ActionListener class that adds a new deck
class AddListener implements ActionListener {
    private final QueueGUI queueGUI;

    public AddListener(QueueGUI queueGUI) {
        this.queueGUI = queueGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addNewDeck();
    }

    // MODIFIES: this
    // EFFECTS: adds a new deck to both the ./data/ folder and the scroll pane
    //          with user-input name
    private void addNewDeck() {
        JPanel addNewDeckPanel = new JPanel();

        String s = JOptionPane.showInputDialog(
                addNewDeckPanel,
                "Give the deck a name:",
                "Add a new deck",
                JOptionPane.PLAIN_MESSAGE);

        if (s.equals("") || alreadyInList(s)) {
            Toolkit.getDefaultToolkit().beep();
            //employeeName.requestFocusInWindow();
            //employeeName.selectAll();
            return;
        }

        int index = queueGUI.queues.getSelectedIndex(); //get selected index
        if (index == -1) { //no selection, so insert at beginning
            index = 0;
        } else {           //add after the selected item
            index++;
        }

        queueGUI.listModel.insertElementAt(s, index);

        queueGUI.queues.setSelectedIndex(index);
        queueGUI.queues.ensureIndexIsVisible(index);

        CardQueue addedQueue = new CardQueue(s);
        saveQueue(s, addedQueue);
    }

    // adapted from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    // EFFECTS: return true if the deck name user types already exists, false otherwise
    protected boolean alreadyInList(String s) {
        return queueGUI.listModel.contains(s);
    }

    // EFFECTS: save the user created deck to ./data/ folder
    private void saveQueue(String s, CardQueue addedQueue) {
        String queueFile = "./data/" + s + ".txt";
        try {
            Writer writer = new Writer(new File(queueFile));
            writer.write(addedQueue);
            writer.close();
        } catch (FileNotFoundException e) {
            //System.out.println("Unable to save accounts to " + queueFile);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }
}
