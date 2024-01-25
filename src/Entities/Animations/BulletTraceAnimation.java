package Entities.Animations;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;

import java.util.Random;
import java.util.HashSet;
import java.util.Iterator;

import Entities.Animatable;

/**
 * Diese Klasse stellt die Schusslaufbahn des Schusses einer Waffe visuell dar.
 */
public class BulletTraceAnimation implements Animatable {
    private short animationTime = 30;
    private short animationTimer = 0;
    private Random random = new Random();
    private HashSet<TraceParticle> traceParticles = new HashSet<TraceParticle>();

    public BulletTraceAnimation(Line line) {
        animate();
        createTraceParticles(line);
    }
    
    public void createTraceParticles(Line line) {
        for (int i = 0; i < (line.length() / 4); i++) {
            float randomFloat = random.nextFloat();
            float x = line.getStart().getX() + randomFloat * (line.getEnd().getX() - line.getStart().getX());
            float y = line.getStart().getY() + randomFloat * (line.getEnd().getY() - line.getStart().getY());
            TraceParticle traceParticle = new TraceParticle(x + (random.nextFloat() * 3), y + (random.nextFloat() * 3), animationTime);
            traceParticles.add(traceParticle);
        }
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

        Iterator<TraceParticle> it = traceParticles.iterator();
        while (it.hasNext()) {
            TraceParticle traceParticle = it.next();
            traceParticle.update();
        }
    }

    @Override
    public void render(Graphics g) {
        if (animationTimer > 0) {
            Color color = new Color(200, 200, 200, animationTimer);
            g.setColor(color);

            Iterator<TraceParticle> it = traceParticles.iterator();
            while (it.hasNext()) {
            TraceParticle traceParticle = it.next();
            traceParticle.render(g);
        }
        }
    }

    public short getAnimationTimer() {
        return animationTimer;
    }
    
}
