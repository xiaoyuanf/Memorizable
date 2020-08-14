package ui.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// ActionListener class that deletes selected deck
public class DelListener implements ActionListener {
    private final QueueGUI qgui;

    public DelListener(QueueGUI qgui) {
        this.qgui = qgui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //This method can be called only if
        //there's a valid selection
        //so go ahead and remove whatever's selected.
        int index = qgui.queues.getSelectedIndex();
        deleteSelection(index);
    }

    // MODIFIES: this
    // EFFECTS: deletes selected deck from both ./data/ and the scroll pane
    private void deleteSelection(int index) {
        String queueName = qgui.listModel.getElementAt(index).toString();

        JPanel questionPanel = new JPanel();
        int n = JOptionPane.showConfirmDialog(
                questionPanel,
                "Are you sure you want to permanently delete " + queueName + " ?",
                "",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (n == JOptionPane.YES_OPTION) {
            try {
                Files.delete(Paths.get("./data/" + queueName + ".txt"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            qgui.listModel.remove(index);
        }

        int size = qgui.listModel.getSize();

        if (size == 0) { //No deck left, disable delete.
            qgui.delQueueButton.setEnabled(false);
        }
    }
}
