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

    public Computer(float centerX, float centerY, float spawnRangeX, float spawnRangeY) throws SlickException {
        //TODO:
        super("assets/enemySprites/computer.png", centerX, centerY, 0);
        this.hitpoints = 200;
        this.spawnRangeX = spawnRangeX;
        this.spawnRangeY = spawnRangeY;
    }

    @Override
    public void render(Graphics g) {
        //TODO: add different texture
        if (isDestroyed) {
            float x = this.shape.getX();
            float y = this.shape.getY();
            this.sprite.draw(x, y);
        } else {
            float x = this.shape.getX();
            float y = this.shape.getY();
            this.sprite.draw(x, y);
        }
        
    }

    @Override
    public void die() {
        //TODO:
    	try {
			Image image = new Image("assets/enemySprites/computer_broken.png");
			this.sprite = image.getScaledCopy(2);
			DamageAnimation brokenComputer = new DamageAnimation("assets/enemySprites/computer_broken.png", this.getShape().getCenterX(), this.getShape().getCenterY(), this.direction);
			this.damageAnimation = brokenComputer;
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.isDestroyed = true;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
    
    public float getSpawnRangeX() {
    	return this.spawnRangeX;
    }
    
    public float getSpawnRangeY() {
    	return this.spawnRangeY;
    }
    
}
