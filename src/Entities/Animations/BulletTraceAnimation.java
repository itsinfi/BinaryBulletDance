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
    private boolean entityIsEnemy;


    /**
     * Diese Methode erzeugt eine Schusslaufbahnanimation
     * 
     * @param line Linie der Schusslaufbahn
     * @param entityIsEnemy True falls die Entität ein Gegner ist, False falls sie ein Spieler ist
     */
    public BulletTraceAnimation(Line line, boolean entityIsEnemy) {
        animate();
        createTraceParticles(line);
        this.entityIsEnemy = entityIsEnemy;
    }
    

    /**
     * Diese Methode erstellt die Partikel, welche auf der Linie platziert werden sollen
     * 
     * @param line Linie der Schusslaufbahn
     */
    public void createTraceParticles(Line line) {

        //1/4 der Länge in Pixel an Partikeln hinzufügen
        for (int i = 0; i < (line.length() / 4); i++) {

            //Zufälligen Wert für Position auf der Linie bestimmen
            float randomFloat = random.nextFloat();

            //x- und y-Koordinaten ausrechnen
            float x = line.getStart().getX() + randomFloat * (line.getEnd().getX() - line.getStart().getX());
            float y = line.getStart().getY() + randomFloat * (line.getEnd().getY() - line.getStart().getY());

            //Partikelanimation erstellen (mit zufälliger Platzierung um der Linie herum)
            TraceParticle traceParticle = new TraceParticle(x + (random.nextFloat() * 6), y + (random.nextFloat() * 6),
                    animationTime);

            //Partikel der Liste an Partikeln hinzufügen
            traceParticles.add(traceParticle);
        }
    }

    /**
     * Diese Methode startet den Animationstimer.
     */
    @Override
    public void animate() {
        animationTimer = animationTime;
    }

    /**
     * Diese Methode updatet den Animationstimer und startet die update-Methode aller Partikel.
     */
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

    /**
     * Diese Methode setzt eine Farbe und startet die render-Funktion aller Partikel.
     * 
     * @param g Grafische Darstellung des Spiels durch die Slick2D-Library
     */
    @Override
    public void render(Graphics g) {
        if (animationTimer > 0) {
            Color color = entityIsEnemy ? new Color(255, 90, 90, animationTimer * 4) : new Color(220, 170, 100, animationTimer * 4);
            g.setColor(color);

            Iterator<TraceParticle> it = traceParticles.iterator();
            while (it.hasNext()) {
                TraceParticle traceParticle = it.next();
                traceParticle.render(g);
            }
        }
    }

    /**
     * Diese Methode gibt den aktuellen Wert des Animationstimers aus.
     * 
     * @return Aktueller Wert des Animationstimers
     */
    public short getAnimationTimer() {
        return animationTimer;
    }
    
}
