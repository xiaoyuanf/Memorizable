package ui.gui;

import exception.EmptyQuestionException;
import exception.NoMoreToReviewException;
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

    private CardQueue selectedQueue;
    private String path;

    private Card currCard;

    public ReviewListener(QueueGUI queueGUI) {
        this.queueGUI = queueGUI;
    }

    // MODIFIES: this
    // EFFECTS: if the selected deck is empty, prompts user to add cards,
    //          otherwise review the cards in the deck
    //          or adds cards
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = queueGUI.queues.getSelectedIndex();
        String queueName = queueGUI.listModel.getElementAt(index).toString();
        this.path = "./data/" + queueName + ".txt";
        try {
            this.selectedQueue = Reader.readCardQueue(new File(path));
        } catch (IOException ioException) {
            //
        }

        if (selectedQueue.getSize() == 0) {
            this.reviewCardFrame = createAddCardDialog("Add some cards to the deck first!");
            addCardDialogComponents(reviewCardFrame);
        } else {
            try {
                currCard = selectedQueue.peekNextCard();
                this.reviewCardFrame = new ReviewCardDialog(queueGUI);
            } catch (NoMoreToReviewException exception) {
                JOptionPane.showMessageDialog(null, "All cards due today have been reviewed! Try add some new cards.");
                this.reviewCardFrame = createAddCardDialog("Add some cards to the deck!");
                addCardDialogComponents(reviewCardFrame);
            }
        }
    }

    private void addCardDialogComponents(JDialog reviewCardFrame) {
        this.btnPanel = createButtonPanel();
        JButton addBtn = new JButton("Add");
        reviewCardFrame.add(btnPanel, BorderLayout.SOUTH);
        btnPanel.add(addBtn);
        //JPanel addNewCardPanel = TextAreas();
        reviewCardFrame.add(addTextAreas(), BorderLayout.CENTER);
        addBtn.addActionListener(new AddCardListener());
    }

    // obtained from https://blog.csdn.net/ygl19920119/article/details/79723512
    // EFFECTS: creates a new dialog with given title
    private JDialog createAddCardDialog(String title) {
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

    // MODIFIES: this
    // EFFECTS: create text areas where user can type the question and answer of a card
    private JPanel addTextAreas() {
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
        //this.reviewCardFrame.add(addNewCardPanel, BorderLayout.CENTER);
        return addNewCardPanel;
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

        // EFFECTS: saves the selected queue
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

    // inner ActionListener class that adds cards while reviewing
    private class AddCardWhileReviewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            reviewCardFrame = createAddCardDialog("Add some cards to the deck first!");
            addCardDialogComponents(reviewCardFrame);
        }
    }

    private class ReviewCardDialog extends JDialog {
        private JLabel qaLabel = new JLabel();
        private JButton addCard;
        private JButton showAnswer;
        private JButton easyBtn;
        private JButton hardBtn;
        private final QueueGUI queueGUI;
        JDialog myDialog;
        private JPanel btnPanel;
        private JPanel reviewCardPanel;
        CardLayout cl = new CardLayout();
        JPanel answerSide = new JPanel();
        JPanel questionSide = new JPanel();

        public ReviewCardDialog(QueueGUI queueGUI) {
            this.queueGUI = queueGUI;
            this.myDialog = new JDialog((Dialog) null, "");

            myDialog.setBounds(new Rectangle(
                    (int) queueGUI.getBounds().getX() + 50,
                    (int) queueGUI.getBounds().getY() + 50,
                    (int) queueGUI.getBounds().getWidth(),
                    (int) queueGUI.getBounds().getHeight()
            ));
            myDialog.setVisible(true);
            myDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JPanel questionSide = createQuestionSide();
            JPanel answerSide = createAnswerSide();

            this.reviewCardPanel = new JPanel();

            reviewCardPanel.setLayout(cl);
            myDialog.add(reviewCardPanel, BorderLayout.CENTER);

            reviewCardPanel.add(questionSide, "q");

            reviewCardPanel.add(answerSide, "a");
        }

        private JPanel createQuestionSide() {
            questionSide.setLayout(new BoxLayout(questionSide, BoxLayout.PAGE_AXIS));
            this.btnPanel = createButtonPanel();
            setQuestionSideBtns(btnPanel);

            JPanel questionPanel = new JPanel();
            questionSide.add(questionPanel, BorderLayout.CENTER);
            questionPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

            questionPanel.add(new JLabel(currCard.getQuestion()));

            btnPanel.add(addCard);
            btnPanel.add(showAnswer);
            questionSide.add(btnPanel, BorderLayout.SOUTH);

            return questionSide;
        }

        private JPanel createAnswerSide() {
            answerSide.setLayout(new BoxLayout(answerSide, BoxLayout.PAGE_AXIS));
            this.btnPanel = createButtonPanel();

            setAnswerSideBtns(btnPanel);

            JPanel answerPanel = new JPanel();
            answerSide.add(answerPanel, BorderLayout.CENTER);
            answerPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

            answerPanel.add(new JLabel(currCard.getAnswer()));

            answerSide.add(btnPanel, BorderLayout.SOUTH);

            return answerSide;
        }

        private JPanel createButtonPanel() {
            JPanel btnPanel = new JPanel();
            btnPanel.setLayout(new FlowLayout());
            btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            return btnPanel;
        }

        private void setAnswerSideBtns(JPanel btnPanel) {
            JButton addCardButton = new JButton("Add a new card");
            addCardButton.addActionListener(new AddCardWhileReviewListener());
            hardBtn = new JButton("Hard");
            hardBtn.addActionListener(new HardActionListener());
            easyBtn = new JButton("Easy");
            easyBtn.addActionListener(new EasyActionListener());

            btnPanel.add(addCardButton);
            btnPanel.add(hardBtn);
            btnPanel.add(easyBtn);
        }

        private void setQuestionSideBtns(JPanel btnPanel) {
            addCard = new JButton("Add a new card");
            addCard.addActionListener(new AddCardWhileReviewListener());

            showAnswer = new JButton("Show answer");
            showAnswer.addActionListener(new ShowAnswerListener());

            btnPanel.add(addCard);
            btnPanel.add(showAnswer);
        }

        private class ShowAnswerListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    qaLabel.setText(selectedQueue.peekNextCard().getAnswer());
                } catch (NoMoreToReviewException exception) {
                    exception.printStackTrace();
                }
                answerSide.removeAll();
                answerSide.repaint();
                answerSide.revalidate();

                answerSide.setLayout(new BoxLayout(answerSide, BoxLayout.PAGE_AXIS));
                btnPanel = createButtonPanel();

                setAnswerSideBtns(btnPanel);

                JPanel answerPanel = new JPanel();
                answerSide.add(answerPanel, BorderLayout.CENTER);
                answerPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

                answerPanel.add(qaLabel);

                answerSide.add(btnPanel, BorderLayout.SOUTH);

                cl.show(reviewCardPanel, "a");
            }
        }

        private class HardActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedQueue.updateQueue(false);
                saveQueue(ReviewListener.this.path, ReviewListener.this.selectedQueue);
                try {
                    qaLabel.setText(selectedQueue.peekNextCard().getQuestion());
                    questionSide.removeAll();
                    questionSide.repaint();
                    questionSide.revalidate();
                    questionSide.setLayout(new BoxLayout(questionSide, BoxLayout.PAGE_AXIS));
                    btnPanel = createButtonPanel();
                    setQuestionSideBtns(btnPanel);

                    //addReviewCardQuestionPanel();
                    JPanel questionPanel = new JPanel();
                    questionSide.add(questionPanel, BorderLayout.CENTER);
                    questionPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
                    questionPanel.add(qaLabel);

                    questionSide.add(btnPanel, BorderLayout.SOUTH);
                    cl.show(reviewCardPanel, "q");
                } catch (NoMoreToReviewException exception) {
                    JOptionPane.showMessageDialog(null, "Congratulations! All cards due today have been reviewed!");
                }
            }
        }

        private class EasyActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedQueue.updateQueue(true);
                saveQueue(ReviewListener.this.path, ReviewListener.this.selectedQueue);

                try {
                    qaLabel.setText(selectedQueue.peekNextCard().getQuestion());
                    questionSide.removeAll();
                    questionSide.repaint();
                    questionSide.revalidate();
                    questionSide.setLayout(new BoxLayout(questionSide, BoxLayout.PAGE_AXIS));
                    btnPanel = createButtonPanel();
                    setQuestionSideBtns(btnPanel);

                    //addReviewCardQuestionPanel();
                    JPanel questionPanel = new JPanel();
                    questionSide.add(questionPanel, BorderLayout.CENTER);
                    questionPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
                    questionPanel.add(qaLabel);

                    questionSide.add(btnPanel, BorderLayout.SOUTH);

                    cl.show(reviewCardPanel, "q");
                } catch (NoMoreToReviewException exception) {
                    JOptionPane.showMessageDialog(null, "Congratulations! All cards due today have been reviewed!");
                }
            }

        }

        // EFFECTS: saves the selected queue
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
    }
}
