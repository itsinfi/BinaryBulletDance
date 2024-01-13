package Level;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Diese Klasse stellt das Level dar.
 * 
 * @author Hendrik Gomez
 */
public class Level {


    //Attribute

    private Image mapAsset;
    

    //Konstruktoren

    //TODO:
    /**
     * 
     * @param imagePath
     * @throws SlickException
     */
    public Level(String imagePath) throws SlickException {
        mapAsset = new Image(imagePath);
    }


    //Getter


    //Setter
    

    //Methoden

    //TODO:
    /**
     * 
     * @param g
     * @throws SlickException
     */
    public void render(Graphics g) throws SlickException {
        mapAsset.draw(0, 0);
    }
    
}
