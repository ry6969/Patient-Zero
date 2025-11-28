package exception;

/**
 * InvalidChoiceException - Thrown when player input is invalid
 * Used for out-of-range choices or malformed input
 */
public class InvalidChoiceException extends Exception {
    
    public InvalidChoiceException(String message) {
        super(message);
    }

    public InvalidChoiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
