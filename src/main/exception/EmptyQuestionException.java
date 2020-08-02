package exception;

// thrown by Card constructor
public class EmptyQuestionException extends Exception {
    public EmptyQuestionException(String msg) {
        super(msg);
    }
}
