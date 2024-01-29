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

    public static float getX() {
        return xPos;
    }

    public static float getY() {
        return yPos;
    }


    //Setter

    public static void setPosition(float x, float y) {
        xPos = x;
        yPos = y;
    }


    //Methoden

    public static void tranlateTiledCoordinates(Graphics g) {
        g.translate(-xPos, -yPos);
    }
    
}
