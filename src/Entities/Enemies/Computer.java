package Entities.Enemies;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Entities.Enemy;

/**
 * This class spawns new enemies and needs to be destroyed in order for the player to win the game.
 * 
 * @author Jeremy Adam
 */
public class Computer extends Enemy {

    private boolean isDestroyed = false;

    public Computer(float centerX, float centerY) throws SlickException {
        //TODO:
        super("assets/playertestOld.png", centerX, centerY, 0);
        this.hitpoints = 200;
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
        this.isDestroyed = true;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
    
}
