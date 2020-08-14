package ui.gui;

import exception.EmptyQuestionException;
import model.Card;
import model.CardQueue;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

// ActionListener class that reviews a deck
class ReviewListener implements ActionListener {
    private final QueueGUI queueGUI;
    private JDialog reviewCardFrame;
    private JTextArea questionArea = new JTextArea(3, 20);
    private JTextArea answerArea = new JTextArea(3, 20);
    private JPanel btnPanel;

    //private JButton addCardButton;
    //private JButton showAnswerButton;

    private CardQueue selectedQueue;
    private String path;

    private JPanel reviewCardPanel;

    public ReviewListener(QueueGUI queueGUI) {
        this.queueGUI = queueGUI;
    }

    // MODIFIES: this
    // EFFECTS: if the selected deck is empty, prompts user to add cards,
    //          otherwise review the cards in the deck // TODO
    //          or adds cards
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = queueGUI.queues.getSelectedIndex();
        String queueName = queueGUI.listModel.getElementAt(index).toString();
//        this.path = "./data/" + queueName + ".txt";
        try {
            this.selectedQueue = Reader.readCardQueue(new File("./data/" + queueName + ".txt"));
        } catch (IOException ioException) {
            //
        }

        this.btnPanel = createButtonPanel();
        JButton addCardButton = new JButton("Add a new card");
        if (selectedQueue.getSize() == 0) {
            this.reviewCardFrame = createDialog("Add some cards to the deck first!");
            addCardButton.addActionListener(new AddCardListener());
            btnPanel.add(addCardButton);
            addTextAreas();
        } else {
            this.reviewCardFrame = createDialog("");
            addReviewCardQuestionPanel();
            //JButton addCardButton = new JButton("Add a new card");
            addCardButton.addActionListener(new AddCardWhileReviewListener());

            JButton showAnswerButton = new JButton("Show answer");
            showAnswerButton.addActionListener(new ShowAnswerListener());
            btnPanel.add(addCardButton);
            btnPanel.add(showAnswerButton);
        }
        reviewCardFrame.add(btnPanel, BorderLayout.SOUTH);
    }

    // obtained from https://blog.csdn.net/ygl19920119/article/details/79723512
    // EFFECTS: creates a new dialog with given title
    private JDialog createDialog(String title) {
        JDialog myDialog = new JDialog((Dialog) null, title);
        myDialog.setBounds(new Rectangle(
                (int) queueGUI.getBounds().getX() + 50,
                (int) queueGUI.getBounds().getY() + 50,
                (int) queueGUI.getBounds().getWidth(),
                (int) queueGUI.getBounds().getHeight()
        ));
        myDialog.setVisible(true);
        myDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        return myDialog;
    }

    // EFFECTS: creates a panel that is used to contain buttons
    private JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        return btnPanel;
    }

    private void addReviewCardQuestionPanel() {
        reviewCardPanel = new JPanel();
        this.reviewCardFrame.add(reviewCardPanel, BorderLayout.CENTER);
        reviewCardPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        reviewCardPanel.add(new JLabel(selectedQueue.peekNextCard().getQuestion()));
    }

    // MODIFIES: this
    // EFFECTS: creates buttons for adding cards
    private void createAddNewCardButtons() {
        JButton addCardButton = new JButton("Add a new card");
        addCardButton.addActionListener(new AddCardListener());

        JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new FlowLayout());
        buttonsPane.add(addCardButton);

        buttonsPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        this.reviewCardFrame.add(buttonsPane, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: create text areas where user can type the question and answer of a card
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


    // inner ActionListener class that adds card
    private class AddCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addNewCard();
            ReviewListener.this.answerArea.setText(""); // reset input areas
            ReviewListener.this.questionArea.setText("");
        }

        // MODIFIES: this
        // EFFECTS: gets user input, creates a new card, and adds the card to selected deck
        private void addNewCard() {
            String[] strings = getUserInput();
            Card newCard = null;

            try {
                newCard = new Card(strings[0], strings[1]);
            } catch (EmptyQuestionException e) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
            ReviewListener.this.selectedQueue.addCard(newCard);
            int index = queueGUI.queues.getSelectedIndex();
            String queueName = queueGUI.listModel.getElementAt(index).toString();
            ReviewListener.this.path = "./data/" + queueName + ".txt";
            saveQueue(ReviewListener.this.path, ReviewListener.this.selectedQueue);
        }

        // EFFECTS: saves the selected queue after adding new cards
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

        // EFFECTS: gets an array of String from user input
        private String[] getUserInput() {
            String[] strings = new String[2];

            String q = ReviewListener.this.questionArea.getText();
            String a = ReviewListener.this.answerArea.getText();

            strings[0] = q;
            strings[1] = a;

            return strings;
        }
    }

    // inner ActionListener class that reviews cards
    private class ShowAnswerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ReviewListener.this.reviewCardPanel = new JPanel();
            ReviewListener.this.reviewCardFrame.add(reviewCardPanel, BorderLayout.CENTER);
            reviewCardPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
            reviewCardPanel.add(new JLabel(selectedQueue.peekNextCard().getAnswer()));

            JButton hard = new JButton("Hard");
            hard.addActionListener(new HardActionListener());
            JButton easy = new JButton("Easy");
            easy.addActionListener(new EasyActionListener());
            JButton addNewCard = new JButton("Add a new card");
            addNewCard.addActionListener(new AddCardListener());
            btnPanel.add(addNewCard);
            btnPanel.add(hard);
            btnPanel.add(easy);
            reviewCardFrame.add(btnPanel, BorderLayout.SOUTH);
        }

        // TODO
        private class HardActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }

        // TODO
        private class EasyActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }
    }

    // inner ActionListener class that adds cards while reviewing
    private class AddCardWhileReviewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ReviewListener.this.reviewCardFrame = createDialog("Add some cards to the deck first!");
            createAddNewCardButtons();
            addTextAreas();
        }
    }
}
