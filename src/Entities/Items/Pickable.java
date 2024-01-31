package Entities.Items;

/**
 * Dieses Interface erzwingt die Implementation der Methode pickUp(), um etwas für den Spieler "aufhebbar" zu machen.
 * 
 * @author Sascha Angermann
 */
public interface Pickable {
    
    /**
     * Diese Methode soll von Items implementiert werden, um die Aktion durchzuführen, die passieren soll, wenn ein Spieler das Item aufhebt.
     */
    public void pickUp();

}
