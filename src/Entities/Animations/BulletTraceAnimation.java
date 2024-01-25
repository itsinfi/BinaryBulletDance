package Entities.Animations;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;

import Entities.Animatable;

/**
 * Diese Klasse stellt die Schusslaufbahn des Schusses einer Waffe visuell dar.
 */
public class BulletTraceAnimation implements Animatable {
    private short animationTime = 5;
    private short animationTimer = 0;
    private Graphics g;
    private float xStart;
    private float yStart;
    private float xEnd;
    private float yEnd;

    public BulletTraceAnimation(Line line) {
        this.g = g;
        this.xStart = line.getX1();
        this.yStart = line.getY1();
        this.xEnd = line.getX2();
        this.yEnd = line.getY2();
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
        }
    }

    @Override
    public void render(Graphics g) {
        if (animationTimer > 0) {
            Color color = new Color(200, 200, 200, 30);
            g.setColor(color);
            g.drawLine(xStart, yStart, xEnd, yEnd);
        }
    }

    public short getAnimationTimer() {
        return animationTimer;
    }
    
}
