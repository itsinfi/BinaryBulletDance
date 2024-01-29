package Entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import Controllers.PlayerController;

/**
 * Diese Klasse stellt die für den Spieler einsammelbaren Medkits dar.
 * 
 * @author Hendrik Gomez
 */
public class Medkit extends Entity implements Renderable, Pickable {

    private short healAmount = 100;
    private Sound healSound = new Sound("assets/sounds/player_heal.wav");

    public Medkit(float centerX, float centerY) throws SlickException {
        super("assets/medkit.png", centerX, centerY, 0);
    }

    @Override
    public void pickUp() {
        PlayerController.getPlayer().heal(healAmount);
        healSound.play();
    }

    @Override
    public void render(Graphics g) {
        float x = this.shape.getX();
        float y = this.shape.getY();
        this.sprite.draw(x, y);
    }
}
