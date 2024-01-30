package Entities.Animations;

import org.newdawn.slick.Graphics;

/**
 * Dieses Interface stellt die Basis für jegliche Animationen des Games dar.
 * 
 * @author Sascha Angermann
 */
public interface Animatable {

    /**
     * Diese Methode soll einen Timer zum festlegen der Animationszeit starten
     */
    public void animate();

    /**
     * Diese Methode soll Timer und sonstige Eigenschaften der Animation pro Frame aktualisieren
     */
    public void update();
    
    /**
     * Diese Methode soll die Animationsframe darstellen
     */
    public void render(Graphics g);

}
