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

    //attributes
    private static Level level;
    private static String levelNumber;

    //getters

    /**
     * get width of currently loaded level
     * 
     * @return width of currently loaded level
     */
    public static float getLevelWidth() {
        return level.getWidth();
    }

    /**
     * get height of currently loaded level
     * 
     * @return height of currently loaded level
     */
    public static float getLevelHeight() {
        return level.getHeight();
    }

    /**
     * get x coordinate of camera
     * 
     * @return x coordinate of camera
     */
    public static float getCameraX() {
        return Camera.getX();
    }

    /**
     * get y coordinate of camera
     * 
     * @return y coordinate of camera
     */
    public static float getCameraY() {
        return Camera.getY();
    }

    /**
     * get if there is a wall placed on a certain coordinate
     * 
     * @param xPos x coordiante
     * @param yPos y coordinate
     * 
     * @return
     * true -> wall/collision is there
     * false -> there is no wall/collsion there
     */
    public static boolean getIsHittingCollision(float xPos, float yPos) {
        int x = (int) (xPos / 64);
        int y = (int) (yPos / 64);
        boolean[][] collisionMap = level.getCollisionMap();
        if (x < 0 || x >= collisionMap.length || y < 0 || y >= collisionMap[0].length) {
            return true;
        }
        return collisionMap[x][y];
    }
    
    /**
     * get if an entity (or shape) is touching a wall
     * 
     * @param shape shape of entity (or any shape)
     * @return
     * true -> shape is hitting a collision
     * false -> shaoe is not hitting a collision
     */
    public static boolean getIsHittingCollision(Shape shape) {

        int x = (int) ((shape.getX() - shape.getWidth()) / 64);
        int y = (int) ((shape.getY() - shape.getHeight()) / 64);

        boolean[][] collisionMap = level.getCollisionMap();

        if (x < 0 || x >= collisionMap.length || y < 0 || y >= collisionMap[0].length) {
            return true;
        }

        return collisionMap[x][y];
    }

    /**
     * get level number
     * 
     * @return number of the currently loaded level
     */
    public static String getLevelNumber() {
        return levelNumber;
    }

    //METHODS
    /**
     * loads a level
     * 
     * @param levelNumber number of level
     * 
     * @throws SlickException if level could not be loaded
     */
    public static void loadLevel(String lvlNumber) throws SlickException {
        levelNumber = lvlNumber;
        level = new Level("src/Level/Tiled/Levels/Level" + levelNumber + ".tmx");
    }

    /**
     * update currently loaded level
     * 
     * @param container container of the window
     * @param delta milliseconds since last frame
     */
    public static void update(GameContainer container, int delta) {
        Player player = PlayerController.getPlayer();

        float cameraX = player.getShape().getCenterX() - container.getWidth() / 2;
        float cameraY = player.getShape().getCenterY() - container.getHeight() / 2;

        cameraX = Math.max(0, Math.min(cameraX, level.getWidth() - container.getWidth()));
        cameraY = Math.max(0, Math.min(cameraY, level.getHeight() - container.getHeight()));

        Camera.setPosition(cameraX, cameraY);
    }

    /**
     * render currently loaded level
     * 
     * @param g graphics from slick2d
     * 
     * @throws SlickException error when rendering tiled map
     */
    public static void render(Graphics g) throws SlickException {
        level.render();
    }

    /**
     * translate camera movement to tiled coordinates (x => -x and y => -y)
     * 
     * @param g graphics from slick2d
     */
    public static void translateCamera(Graphics g) {
        Camera.tranlateTiledCoordinates(g);
    }

    /**
     * reset camera translation from translate camera
     * 
     * @param g graphics from slick2d
     */
    public static void resetCameraTranslation(Graphics g) {
        g.resetTransform();
    }

}
