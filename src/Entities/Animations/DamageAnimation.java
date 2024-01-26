package Entities.Animations;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Entities.Animatable;
import Entities.Entity;
import Entities.Renderable;

public class DamageAnimation extends Entity implements Animatable, Renderable {

    short animationTime = 20;
    short animationTimer;

    public DamageAnimation(String spriteAsset, float centerX, float centerY, float direction) throws SlickException {
        super(spriteAsset, centerX, centerY, direction);
    }

    @Override
    public void animate() {
        animationTimer = animationTime;
    }

    @Override
    public void update() {
        if (animationTimer > 0) {
            animationTimer--;
        }
    }

    @Override
    public void render(Graphics g) {
        if (animationTimer > 0) {
            Color damageColor = new Color(255, 0, 0, animationTimer * 10);
            float x = this.shape.getX();
            float y = this.shape.getY();
            this.sprite.setRotation(direction);
            this.sprite.draw(x, y, damageColor);
        }
    }
    
}
