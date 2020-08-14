package ui.gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;

// creates a list of decks where the decks can be added, reviewed or deleted
public class QueueGUI extends JPanel implements ListSelectionListener {
    protected MemorizableGUI memoGUI;
    protected JList queues;
    protected DefaultListModel listModel;

    protected JButton addQueueButton;
    protected JButton delQueueButton;
    protected JButton reviewQueueButton;

    // EFFECTS: creates the frame that contains a list of decks and three buttons
    public QueueGUI(MemorizableGUI memoGUI) {
        this.memoGUI = memoGUI;
        createQueueScrollPane();
        createButtons();
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to QueueGUI
    private void createButtons() {
        this.addQueueButton = new JButton("Add a new deck");
        addQueueButton.addActionListener(new AddListener(this));

        this.delQueueButton = new JButton("Delete the deck");
        delQueueButton.addActionListener(new DelListener(this));
        delQueueButton.setEnabled(false);

        this.reviewQueueButton = new JButton("Review the deck");
        reviewQueueButton.addActionListener(new ReviewListener(this));
        reviewQueueButton.setEnabled(false);

        JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new FlowLayout());
        buttonsPane.add(reviewQueueButton);
        buttonsPane.add(delQueueButton);
        buttonsPane.add(addQueueButton);

        buttonsPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        add(buttonsPane, BorderLayout.SOUTH);
    }

    // adapted from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
    // MODIFIES: this
    // EFFECTS: creates a scroll pane that contains list of decks got from ./data/
    private void createQueueScrollPane() {
        setLayout(new BorderLayout(20, 20));

        listModel = new DefaultListModel();

        File dir = new File("./data/");
        File[] files = dir.listFiles(file -> !file.isHidden());
        if (files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                String queuePath = files[i].getName();
                listModel.addElement(queuePath.substring(0, queuePath.length() - 4));
            }
        }

        // Create the list and put it in a scroll pane
        this.queues = new JList(listModel);
        queues.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        queues.addListSelectionListener(this);
        JScrollPane queueScrollPane = new JScrollPane(queues);

        JPanel queuesPane = new JPanel();
        queuesPane.setLayout(new BoxLayout(queuesPane, BoxLayout.PAGE_AXIS));
        JLabel queueLabel = new JLabel("Pick a deck");
        queueLabel.setLabelFor(queues);

        queuesPane.add(queueLabel);
        queuesPane.add(queueScrollPane);
        queuesPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(queuesPane);
    }


    // MODIFIES: this
    // EFFECTS: Required by ActionListener. Enable/disable buttons given selection
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (queues.getSelectedIndex() == -1) {
                //No selection, disable the delete button.
                delQueueButton.setEnabled(false);
                reviewQueueButton.setEnabled(false);

            } else {
                //Selection, enable the delete button.
                delQueueButton.setEnabled(true);
                reviewQueueButton.setEnabled(true);
            }
        }
    }
}