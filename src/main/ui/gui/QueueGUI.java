package ui.gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

// adapted from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
// create a list of decks where the decks can be added or deleted
public class QueueGUI extends JPanel implements ListSelectionListener {
    private MemorizableGUI memoGUI;
    private JList queues;
    private DefaultListModel listModel;
    private static final String addQueueString = "Add a new deck";
    private static final String delQueueString = "Delete the deck";
    private JButton addQueueButton;
    private JButton delQueueButton;

    public QueueGUI(MemorizableGUI memoGUI) {
        this.memoGUI = memoGUI;
        createQueueScrollPane();
        createButtons();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    private void createButtons() {
        JButton addQueueButton = new JButton("Add a new deck");

        JButton delQueueButton = new JButton("Delete the deck");

        addQueueButton.setActionCommand("add");
        addQueueButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });

        JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new FlowLayout());
        buttonsPane.add(addQueueButton);
        buttonsPane.add(delQueueButton);

        buttonsPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        add(buttonsPane, BorderLayout.SOUTH);
    }

    private void createQueueScrollPane() {
        setLayout(new BorderLayout(20, 20));

        listModel = new DefaultListModel();

        File dir = new File("./data/");
        File[] files = dir.listFiles(file -> !file.isHidden());
        for (int i = 0; i < files.length; i++) {
            String queuePath = files[i].getName();
            listModel.addElement(queuePath.substring(0, queuePath.length() - 4));
        }

        // Create the list and put it in a scroll pane
        queues = new JList(listModel);
        queues.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        queues.addListSelectionListener(this);
        JScrollPane queueScrollPane = new JScrollPane(queues);

        JPanel queuesPane = new JPanel();
        queuesPane.setLayout(new BoxLayout(queuesPane, BoxLayout.PAGE_AXIS));
        JLabel queueLabel = new JLabel("Pick a deck to review");
        queueLabel.setLabelFor(queues);

        queuesPane.add(queueLabel);
        queuesPane.add(queueScrollPane);
        queuesPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(queuesPane);
    }
}
