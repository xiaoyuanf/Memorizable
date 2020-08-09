package ui.gui.buttons;


import ui.gui.MemorizableGUI;

import javax.swing.*;

// creates buttons for the memoGUI
// adapted from https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete/blob/master/src/ui/tools/Tool.java
public abstract class LowerPanelButton {
    protected JButton button1;
    protected JButton button2;
    protected MemorizableGUI memoGUI;

    public LowerPanelButton(MemorizableGUI memoGUI, JComponent parent) {
        this.memoGUI = memoGUI;
        createButton(parent);
        addToParent(parent);
        addListener();
    }

    protected void addToParent(JComponent parent) {
        parent.add(button1);
        parent.add(button2);
    }

    protected abstract void createButton(JComponent parent);

    protected abstract void addListener();
}

