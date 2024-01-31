package Entities.Animations;

import java.util.Arrays;
import java.util.HashSet;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

/**
 * Diese Methode animiert das Schweben von fliegenden Objekten
 */
public class FlyingAnimation implements Animatable {

    private HashSet<Shape> animatedShapes = new HashSet<Shape>();
    private short animationTimer;
    private short animationTime = 240;

    /**
     * Konstruktor für die Animation
     * 
     * @param shape Alle zugehörigen Objekte einer Entität, die schweben soll
     */
    public FlyingAnimation(Shape[] shapes) {
        this.animatedShapes.addAll(Arrays.asList(shapes));
        animate();
    }

    /**
     * Diese Methode startet den Animationstimer
     */
    @Override
    public void animate() {
        animationTimer = animationTime;
    }

    /**
     * Diese Methode updated den Animationstimer im Loop
     */
    @Override
    public void update() {
        if (animationTimer > 0) {
            animationTimer--;
        } else {
            animate();
        }
    }

    /**
     * Diese Methode verändert die Position der Form um so einen "Schwebeeffekt" zu erzeugen
     */
    @Override
    public void render(Graphics g) {
        float modifier = (float) animationTimer / animationTime;

        float offsetY = 1 * (float) Math.cos(2 * Math.PI * modifier);

        for (Shape animatedShape : this.animatedShapes) {
            animatedShape.setY(animatedShape.getY() + offsetY);
        }
    }
    
}
