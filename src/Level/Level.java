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

    // TODO:
    /**
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

    public float getWidth() {
        return tiledLevel.getWidth() * 64;
    }

    public float getHeight() {
        return tiledLevel.getHeight() * 64;
    }

    public boolean[][] getCollisionMap() {
        return collisionMap;
    }

    // Setter

    // Methoden

    private TiledMap loadLevel(String levelPath) throws SlickException {
        
        try {
            tiledLevel = new TiledMap(levelPath);
        } catch (SlickException e) {
            e.printStackTrace();
            throw new SlickException("Error loading TiledMap: " + levelPath, e);
        }
        
        return tiledLevel;
    }

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

        collisionMap[0][0] = true;
        collisionMap[0][1] = true;
        collisionMap[0][2] = true;
        collisionMap[0][3] = true;
        collisionMap[0][4] = true;
        collisionMap[0][5] = true;
        collisionMap[1][0] = true;
        collisionMap[2][0] = true;
        for (int i = 0; i < 32; i++) {
            for (int k = 0; k < 32; k++) {
                System.out.println(collisionMap[i][k]);
            }
        }
        return collisionMap;
    }

    // TODO:
    /**
     * 
     * @param g
     * @throws SlickException
     */
    public void render() throws SlickException {
        tiledLevel.render(0, 0);
    }

    public void render(int pixelX, int pixelY) throws SlickException {
        tiledLevel.render(pixelX, pixelY);
    }
}
