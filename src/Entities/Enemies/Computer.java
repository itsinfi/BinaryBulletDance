package Entities.Enemies;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Entities.Enemy;
import Entities.Animations.DamageAnimation;

/**
 * This class spawns new enemies and needs to be destroyed in order for the player to win the game.
 * 
 * @author Jeremy Adam
 */
public class Computer extends Enemy {

    private boolean isDestroyed = false;
	private float spawnRangeX;
    private float spawnRangeY;


    /**
     * creates a computer enemy type
     * 
     * @param centerX center x coordinate
     * @param centerY center y coordinate
     * @param spawnRangeX x range to spawn enemies around computer
     * @param spawnRangeY y range to spawn enemies around computer
     * @throws SlickException when asset could not be found
     */
    public Computer(float centerX, float centerY, float spawnRangeX, float spawnRangeY) throws SlickException {
        super("assets/enemySprites/computer.png", centerX, centerY, 0);
        this.hitpoints = 800;
        this.spawnRangeX = spawnRangeX;
        this.spawnRangeY = spawnRangeY;
    }


    /**
     * renders computer
     */
    @Override
    public void render(Graphics g) {
        float x = this.shape.getX();
        float y = this.shape.getY();
        this.sprite.draw(x, y);
    }

    /**
     * changes computer state to destroyed and updates sprite
     */
    @Override
    public void die() {
        try {
            Image image = new Image("assets/enemySprites/computer_broken.png");
            this.sprite = image.getScaledCopy(2);
            DamageAnimation brokenComputer = new DamageAnimation("assets/enemySprites/computer_broken.png",
                    this.getShape().getCenterX(), this.getShape().getCenterY(), this.direction);
            this.damageAnimation = brokenComputer;
        } catch (SlickException e) {
            e.printStackTrace();
        }
        this.isDestroyed = true;
    }

    /**
     * get if computer is destroyed or not
     * 
     * @return True -> computer is destroyed; false -> computer is not destroyed
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }
    
    /**
     * get spawn range for x coordinates
     * 
     * @return spawn range for x coordinates
     */
    public float getSpawnRangeX() {
        return this.spawnRangeX;
    }
    
    /**
     * get spawn range for y coordinates
     * 
     * @return spawn range for y coordinates
     */
    public float getSpawnRangeY() {
    	return this.spawnRangeY;
    }
    
}
