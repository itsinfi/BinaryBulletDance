package Entities.Animations;

import org.newdawn.slick.Graphics;

import java.util.Random;

import Entities.Animatable;

public class TraceParticle implements Animatable {

    private short animationTimer;
    private short animationTime;
    private float xPos;
    float yPos;
    private Random random = new Random();

    public TraceParticle(float xPos, float yPos, short animationTime){
        this.xPos = xPos;
        this.yPos = yPos;
        this.animationTime = animationTime;
        animate();
    }

    @Override
    public void animate() {
        animationTimer = animationTime;
    }

    @Override
    public void update() {
        if (animationTimer > 0) {
            animationTimer--;
            xPos += random.nextFloat() - 0.5;
            yPos += random.nextFloat() - 0.5;
        }
    }

    @Override
    public void render(Graphics g) {
        if (animationTimer > 0) {
            g.fillRect(xPos, yPos, 3f, 3f);
        }
    }
    

}
