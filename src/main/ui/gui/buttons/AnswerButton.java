package ui.gui.buttons;

import ui.gui.MemorizableGUI;

import javax.swing.*;

public class AnswerButton extends LowerPanelButton {
    public AnswerButton(MemorizableGUI memoGUI, JComponent parent) {
        super(memoGUI, parent);
    }

    @Override
    protected void createButton(JComponent parent) {

    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addToParent(JComponent parent) {
        parent.add(button1);
    }
}
