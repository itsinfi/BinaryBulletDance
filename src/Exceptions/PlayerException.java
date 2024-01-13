package Exceptions;

/**
 * Diese Klasse dient als Custom-Exception-Klasse für die Klasse Player.
 * 
 * @author Jeremy Adam
 */
public class PlayerException extends Exception {
    public PlayerException(String message) {
        super(message);
    }
}
