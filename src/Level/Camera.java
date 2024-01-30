package Level;

import org.newdawn.slick.Graphics;

/**
 * Diese Klasse stellt die Kamera des Spiels dar.
 * 
 * @author Sascha Angermann
 */
public abstract class Camera {
    
    //Attribute

    private static float xPos;
    private static float yPos;


    //Getter

    /**
     * Diese Methode gibt die x-Koordinate der Kamera zurück
     * 
     * @return x-Koordinate der Kamera
     */
    public static float getX() {
        return xPos;
    }

    /**
     * Diese Methode gibt die y-Koordinate der Kamera zurück.
     * 
     * @return y-Koordinate der Kamera
     */
    public static float getY() {
        return yPos;
    }


    //Setter

    /**
     * Diese Methode setzt die x- und y-Koordinaten der Kamera fest.
     * 
     * @param x x-Koordinate
     * @param y y-Koordinate
     */
    public static void setPosition(float x, float y) {
        xPos = x;
        yPos = y;
    }


    //Methoden

    /**
     * Diese Methode übersetzt die Kamera in das Format von Tiled:
     * x => -x
     * y => -y
     * 
     * @param g
     */
    public static void tranlateTiledCoordinates(Graphics g) {
        g.translate(-xPos, -yPos);
    }
    
}
