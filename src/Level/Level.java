package Level;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.*;

/**
 * Diese Klasse stellt das Level dar.
 * 
 * @author Hendrik Gomez
 */
public class Level {

    // Attribute

    private String levelPath;
    private TiledMap tiledLevel;

    // Konstruktoren

    // TODO:
    /**
     * 
     * @param levelPath
     * @throws SlickException
     */
    public Level(String levelPath) throws SlickException {
        this.levelPath = levelPath;
        try {
            this.tiledLevel = new TiledMap(levelPath);
        } catch (SlickException e) {
            e.printStackTrace();
            throw new SlickException("Error loading TiledMap: " + levelPath, e);
        }
    }

    // Getter

    // Setter

    // Methoden

    // TODO:
    /**
     * 
     * @param g
     * @throws SlickException
     */
    public void render(Graphics g) throws SlickException {
        tiledLevel.render(0, 0);
    }

}
