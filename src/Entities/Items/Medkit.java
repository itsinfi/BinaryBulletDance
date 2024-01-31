package Entities.Items;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import Controllers.PlayerController;
import Entities.Entity;

/**
 * Diese Klasse stellt die für den Spieler einsammelbaren Medkits dar.
 * 
 * @author Hendrik Gomez
 */
public class Medkit extends Entity implements Pickable {

    private short healAmount = 500;
    private Sound healSound = new Sound("assets/sounds/player_heal.wav");

    /**
     * this method creates a medkit
     * 
     * @param centerX center x coordinate
     * @param centerY center y coordinate
     * @throws SlickException when asset could not be found
     */
    public Medkit(float centerX, float centerY) throws SlickException {
        super("assets/medkit.png", centerX, centerY, 0);
    }

    /**
     * this method heals the player by healAmount
     */
    @Override
    public void pickUp() {
        PlayerController.getPlayer().heal(healAmount);
        healSound.play();
    }

    /**
     * this method renders the medkit
     */
    public void render(Graphics g) {
        float x = this.shape.getX();
        float y = this.shape.getY();
        this.sprite.draw(x, y);
    }
}
