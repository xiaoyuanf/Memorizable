package ui.gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;

// adapted from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
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
        setLayout(new BorderLayout());

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
        queues.setSelectedIndex(0);
        queues.addListSelectionListener(this);
        JScrollPane queueScrollPane = new JScrollPane(queues);

        add(queueScrollPane);

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
