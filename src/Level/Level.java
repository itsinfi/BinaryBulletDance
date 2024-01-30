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
    private boolean[][] collisionMap;

    // Konstruktoren

    /**
     * create Level
     * 
     * @param levelPath
     * @throws SlickException
     */
    
    public Level(String levelPath) throws SlickException {
        this.levelPath = levelPath;
        this.tiledLevel = loadLevel(this.levelPath);
        this.collisionMap = buildCollisionMap(this.tiledLevel);
    }

    // Getter

    /**
     * get level width (1 tile = 64 x 64 pixels)
     * 
     * @return level width
     */
    public float getWidth() {
        return tiledLevel.getWidth() * 64;
    }

    /**
     * get level height (1 tile = 64 x 64 pixels)
     * 
     * @return
     */
    public float getHeight() {
        return tiledLevel.getHeight() * 64;
    }

    /**
     * get collision map (walls, borders etc)
     * 
     * @return collision map of level
     */
    public boolean[][] getCollisionMap() {
        return collisionMap;
    }

    // Setter

    // Methoden

    /**
     * load level
     * 
     * @param levelPath path of tiled level
     * @return map of level
     * @throws SlickException if assets could not be found
     */
    private TiledMap loadLevel(String levelPath) throws SlickException {

        try {
            tiledLevel = new TiledMap(levelPath);
        } catch (SlickException e) {
            e.printStackTrace();
            throw new SlickException("Error loading TiledMap: " + levelPath, e);
        }

        return tiledLevel;
    }

    /**
     * build collision map
     * 
     * @param Level tiled level
     * @return collision map
     */
    private boolean[][] buildCollisionMap(TiledMap Level) {
        collisionMap = new boolean[Level.getWidth()][Level.getHeight()];
        
        for (int x = 0; x < Level.getWidth(); x++) {
            for (int y = 0; y < Level.getHeight(); y++) {

                // get the TileID on coordinate x, y and determin collison
                switch (Level.getTileProperty(Level.getTileId(x, y, 0), "collision", "false")) {

                    case "true":
                        collisionMap[x][y] = true;
                        break;
                    case "false":
                        collisionMap[x][y] = false;
                        break;

                }
            }
        }

        return collisionMap;
    }

    /**
     * render level
     * 
     * @param g graphics from slick2d
     * @throws SlickException rendering level failed
     */
    public void render() throws SlickException {
        tiledLevel.render(0, 0);
    }
}
