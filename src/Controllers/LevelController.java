package Controllers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import Entities.Player;
import Level.Camera;
import Level.Level;

/**
 * Diese Klasse verwaltet das Level des Games.
 * 
 * @author Hendrik Gomez
 */
public abstract class LevelController {

    private static Level level;

    public static float getLevelWidth() {
        return level.getWidth();
    }

    public static float getLevelHeight() {
        return level.getHeight();
    }

    public static float getCameraX() {
        return Camera.getX();
    }

    public static float getCameraY() {
        return Camera.getY();
    }

    public static boolean getIsHittingCollision(float xPos, float yPos) {
        int x = (int) (xPos / 64);
        int y = (int) (yPos / 64);
        boolean[][] collisionMap = level.getCollisionMap();
        if (x < 0 || x > collisionMap.length || y < 0 || y > collisionMap[0].length) {
            return true;
        }
        return collisionMap[x][y];
    }

    public static boolean getIsHittingCollision(Shape shape) {
    	
        int x = (int) ((shape.getX() - shape.getWidth()) / 64);
        int y = (int) ((shape.getY() - shape.getHeight()) / 64);
        
        boolean[][] collisionMap = level.getCollisionMap();
        
        if (x < 0 || x > collisionMap.length || y < 0 || y > collisionMap[0].length) {
            return true;
        }
        
        return collisionMap[x][y];
    }
    
  public static boolean getCollision(float x, float y) {
    	
        int xConverted = (int) (x / 64);
        int yConverted = (int) (y/ 64);
        
        boolean[][] collisionMap = level.getCollisionMap();
        
        if (x < 0 || x > collisionMap.length || y < 0 || y > collisionMap[0].length) {
            return true;
        }
        
        return collisionMap[xConverted][yConverted];
    }


    public static void loadLevel(String levelNumber) throws SlickException {
        level = new Level("src/Level/Tiled/Levels/Level" + levelNumber + ".tmx");
    }

    public static void update(GameContainer container, int delta) {
        Player player = PlayerController.getPlayer();

        float cameraX = player.getShape().getCenterX() - container.getWidth() / 2;
        float cameraY = player.getShape().getCenterY() - container.getHeight() / 2;

        cameraX = Math.max(0, Math.min(cameraX, level.getWidth() - container.getWidth()));
        cameraY = Math.max(0, Math.min(cameraY, level.getHeight() - container.getHeight()));

        Camera.setPosition(cameraX, cameraY);
    }

    public static void render(Graphics g) throws SlickException {
        level.render();
    }

    public static void translateCamera(Graphics g) {
        Camera.tranlateTiledCoordinates(g);
    }

    public static void resetCameraTranslation(Graphics g) {
        g.resetTransform();
    }

}
