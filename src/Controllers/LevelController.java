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

    public static boolean getIsHittingCollision(Shape shape) {
        int x = (int) ((shape.getCenterX() - shape.getWidth()) / 64);
        int y = (int) ((shape.getCenterY() - shape.getHeight()) / 64);
        if (x < 0 || x >= 31 || y < 0 || y >= 32) {
            return true;
        }
        boolean[][] collisionMap = level.getCollisionMap();
        return collisionMap[x][y];
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
