package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// GUI of Memorizable
public class MemorizableGUI extends JFrame {
    private QueueGUI queueArea;

    //EFFECTS: initialize the GUI
    public MemorizableGUI() {
        super("Memorizable");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        queueArea = new QueueGUI(this);
        add(queueArea, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MemorizableGUI();
    }
}
