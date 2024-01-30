package Entities.Animations;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

/**
 * Diese Methode animiert das Schweben von fliegenden Objekten
 */
public class FlyingAnimation implements Animatable {

    private Shape animatedShape;
    private short animationTimer;
    private short animationTime = 240;

    /**
     * Konstruktor für die Animation
     * 
     * @param shape Form des Objekts, welches Schweben soll
     */
    public FlyingAnimation(Shape shape) {
        this.animatedShape = shape;
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

        float offsetY = 5 * (float) Math.cos(2 * Math.PI * modifier);

        this.animatedShape.setY(this.animatedShape.getX() + offsetY);
    }
    
}
