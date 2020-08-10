package ui.gui;

import exception.EmptyQuestionException;
import model.Card;
import model.CardQueue;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

// adapted from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
// create a list of decks where the decks can be added or deleted
public class QueueGUI extends JPanel implements ListSelectionListener {
    private MemorizableGUI memoGUI;
    private JList queues;
    private DefaultListModel listModel;

    private JButton addQueueButton;
    private JButton delQueueButton;
    private JButton reviewQueueButton;

    public QueueGUI(MemorizableGUI memoGUI) {
        this.memoGUI = memoGUI;
        createQueueScrollPane();
        createButtons();
    }

    private void createButtons() {
        this.addQueueButton = new JButton("Add a new deck");
        addQueueButton.setActionCommand("add");
        addQueueButton.addActionListener(new AddListener());

        this.delQueueButton = new JButton("Delete the deck");
        delQueueButton.setActionCommand("delete");
        delQueueButton.addActionListener(new DelListener());
        delQueueButton.setEnabled(false);

        this.reviewQueueButton = new JButton("Review the deck");
        reviewQueueButton.setActionCommand("review");
        reviewQueueButton.addActionListener(new ReviewListener());
        reviewQueueButton.setEnabled(false);

        JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new FlowLayout());
        buttonsPane.add(reviewQueueButton);
        buttonsPane.add(delQueueButton);
        buttonsPane.add(addQueueButton);

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
        JLabel queueLabel = new JLabel("Pick a deck");
        queueLabel.setLabelFor(queues);

        queuesPane.add(queueLabel);
        queuesPane.add(queueScrollPane);
        queuesPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(queuesPane);
    }

    private class DelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = queues.getSelectedIndex();
            deleteSelection(index);
        }

        private void deleteSelection(int index) {
            String queueName = listModel.getElementAt(index).toString();

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
                listModel.remove(index);
            }


            int size = listModel.getSize();

            if (size == 0) { //No deck left, disable delete.
                delQueueButton.setEnabled(false);
            }
        }
    }

    private class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addNewDeck();
        }

        private void addNewDeck() {
            JPanel addNewDeckPanel = new JPanel();

            String s = (String) JOptionPane.showInputDialog(
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

            int index = queues.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.insertElementAt(s, index);

            queues.setSelectedIndex(index);
            queues.ensureIndexIsVisible(index);

            CardQueue addedQueue = new CardQueue(s);
            saveQueue(s, addedQueue);
        }

        protected boolean alreadyInList(String s) {
            return listModel.contains(s);
        }

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

    private class ReviewListener implements ActionListener {
        //private JPanel addNewCardPanel = new JPanel();
        private JDialog reviewCardFrame;
        private JTextArea questionArea = new JTextArea(3, 20);
        private JTextArea answerArea = new JTextArea(3, 20);

        private JButton addCardButton;
        private JButton showAnswerButton;

        private CardQueue selectedQueue;
        private String path;

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = queues.getSelectedIndex();
            String queueName = listModel.getElementAt(index).toString();
            this.path = "./data/" + queueName + ".txt";
            try {
                this.selectedQueue = Reader.readCardQueue(new File(String.valueOf(path)));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            if (selectedQueue.getSize() == 0) {
                createAddCardFrame();
                createAddNewCardButtons();
                addTextAreas();
            } else {
                createViewCardFrame();
                createReviewCardButtons();
            }
        }

        // obtained from https://blog.csdn.net/ygl19920119/article/details/79723512
        private void createAddCardFrame() {
            this.reviewCardFrame = new JDialog((Dialog) null, "Add some cards to the deck first!");
            reviewCardFrame.setBounds(new Rectangle(
                    (int) QueueGUI.this.getBounds().getX() + 50,
                    (int) QueueGUI.this.getBounds().getY() + 50,
                    (int) QueueGUI.this.getBounds().getWidth(),
                    (int) QueueGUI.this.getBounds().getHeight()
            ));
            reviewCardFrame.setVisible(true);
            reviewCardFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }

        private void createViewCardFrame() {
            this.reviewCardFrame = new JDialog();
            reviewCardFrame.setBounds(new Rectangle(
                    (int) QueueGUI.this.getBounds().getX() + 50,
                    (int) QueueGUI.this.getBounds().getY() + 50,
                    (int) QueueGUI.this.getBounds().getWidth(),
                    (int) QueueGUI.this.getBounds().getHeight()
            ));
            reviewCardFrame.setVisible(true);
            reviewCardFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }

        private void createReviewCardButtons() {
            this.addCardButton = new JButton("Add a new card");
            addCardButton.setActionCommand("addCard");
            addCardButton.addActionListener(new AddCardWhileReviewListener());

            this.showAnswerButton = new JButton("Show answer");
            showAnswerButton.setActionCommand("show");
            //showAnswerButton.addActionListener(new ShowAnswerListener());
            //showAnswerButton.setEnabled(false);

            JPanel buttonsPane = new JPanel();
            buttonsPane.setLayout(new FlowLayout());
            buttonsPane.add(addCardButton);
            buttonsPane.add(showAnswerButton);

            buttonsPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

            this.reviewCardFrame.add(buttonsPane, BorderLayout.SOUTH);
        }

        private void createAddNewCardButtons() {
            this.addCardButton = new JButton("Add a new card");
            addCardButton.setActionCommand("addCard");
            addCardButton.addActionListener(new AddCardListener());

            JPanel buttonsPane = new JPanel();
            buttonsPane.setLayout(new FlowLayout());
            buttonsPane.add(addCardButton);

            buttonsPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

            this.reviewCardFrame.add(buttonsPane, BorderLayout.SOUTH);
        }

        private void addTextAreas() {
            JPanel addNewCardPanel = new JPanel();
            addNewCardPanel.setLayout(new BoxLayout(addNewCardPanel, BoxLayout.PAGE_AXIS));
            addNewCardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


            this.questionArea.setAlignmentX(0); // prevents label from moving when typing
            this.answerArea.setAlignmentX(0);

            JLabel questionLabel = new JLabel("Question:");
            questionLabel.setLabelFor(questionArea);
            questionLabel.setAlignmentX(0);
            JLabel answerLabel = new JLabel("Answer:");
            answerLabel.setLabelFor(answerArea);
            answerLabel.setAlignmentX(0);

            addNewCardPanel.add(questionLabel);
            addNewCardPanel.add(questionArea);

            addNewCardPanel.add(answerLabel);
            addNewCardPanel.add(answerArea);
            this.reviewCardFrame.add(addNewCardPanel, BorderLayout.CENTER);
        }

        private class AddCardListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewCard();
                ReviewListener.this.answerArea.setText(""); // reset input areas
                ReviewListener.this.questionArea.setText("");
            }

            private void addNewCard() {
                String[] strings = getUserInput();
                Card newCard = null;

                if (strings[0].equals("")) {
                    Toolkit.getDefaultToolkit().beep();
                        //employeeName.requestFocusInWindow();
                        //employeeName.selectAll();
                    return;
                }

                try {
                    newCard = new Card(strings[0], strings[1]);
                } catch (EmptyQuestionException e) {
                    e.printStackTrace();
                }

                ReviewListener.this.selectedQueue.addCard(newCard);

                saveQueue(ReviewListener.this.path, ReviewListener.this.selectedQueue);
            }

            private void saveQueue(String path, CardQueue selectedQueue) {
                try {
                    Writer writer = new Writer(new File(path));
                    writer.write(selectedQueue);
                    writer.close();
                } catch (FileNotFoundException e) {
                    //System.out.println("Unable to save accounts to " + queueFile);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    // this is due to a programming error
                }
            }

            private String[] getUserInput() {
                String[] strings = new String[2];

                String q = ReviewListener.this.questionArea.getText();
                String a = ReviewListener.this.answerArea.getText();

                strings[0] = q;
                strings[1] = a;

                return strings;
            }
        }

        private class AddCardWhileReviewListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAddCardFrame();
                createAddNewCardButtons();
                addTextAreas();
            }
        }
    }



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
