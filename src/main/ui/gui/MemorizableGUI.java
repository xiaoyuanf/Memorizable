package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MemorizableGUI extends JFrame {
    private QueueGUI queueArea;

    public MemorizableGUI() {
        super("Memorizable");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton addQueueButton = new JButton("Add a new deck");
        addQueueButton.setActionCommand("add");
        addQueueButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });

        queueArea = new QueueGUI(this);
        add(queueArea, BorderLayout.CENTER);
        add(addQueueButton, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MemorizableGUI();
    }
}
