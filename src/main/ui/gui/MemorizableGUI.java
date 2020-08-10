package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MemorizableGUI extends JFrame {
    private QueueGUI queueArea;

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
