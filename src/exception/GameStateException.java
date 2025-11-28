package exception;

/**
 * GameStateException - Thrown when an invalid game state is detected
 * Used for inconsistencies like missing nodes, null player state, etc.
 */
public class GameStateException extends Exception {
    
    public GameStateException(String message) {
        super(message);
    }

    public GameStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
