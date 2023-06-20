/** Thrown when attempting to add an element to a full stack. */
public class StackOverflowException extends StackException {
    public StackOverflowException() {}

    public StackOverflowException(String message) {
        super(message);
    }

    public StackOverflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
