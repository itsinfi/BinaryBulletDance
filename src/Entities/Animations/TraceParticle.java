package Entities.Animations;

import org.newdawn.slick.Graphics;

import java.util.Random;

/**
 * Diese Klasse stellt einen Patikel innerhalb der Schusslaufbahnanimation einer Waffe dar.
 */
public class TraceParticle implements Animatable {

    private short animationTimer;
    private short animationTime;
    private float xPos;
    private float yPos;
    private Random random = new Random();


    /**
     * Diese Methode erzeugt einen Schusslaufbahnpartikel
     * 
     * @param xPos x-Position
     * @param yPos y-Position
     * @param animationTime Dauer der Animation
     */
    public TraceParticle(float xPos, float yPos, short animationTime) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.animationTime = animationTime;
        animate();
    }


    /**
     * Diese Methode startet den Animationstimer.
     */
    @Override
    public void animate() {
        animationTimer = animationTime;
    }

    /**
     * Diese Methode aktualisiert die Position des Partikels zufällig und aktualisiert den Animationstimer.
     */
    @Override
    public void update() {
        if (animationTimer > 0) {
            animationTimer--;
            xPos += random.nextFloat() - 0.5;
            yPos += random.nextFloat() - 0.5;
        }
    }

    /**
     * Diese Methode stellt den Partikel visuell dar.
     * 
     * @param g Grafische Darstellung des Spiels durch die Slick2D-Library
     */
    @Override
    public void render(Graphics g) {
        if (animationTimer > 0) {
            g.fillRect(xPos, yPos, 3f, 3f);
        }
    }
    

}
