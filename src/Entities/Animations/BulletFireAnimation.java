package Entities.Animations;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Entities.Animatable;
import Entities.Entity;

/**
 * Diese Klasse stellt das Schussfeuer einer Waffe dar.
 * 
 * @author Sascha Angermann
 */
public class BulletFireAnimation extends Entity implements Animatable {


    //Attribute

    private short animationTime = 5;
    private short animationTimer = 0;
    private float offsetX;
    private float offsetY;
    private Random random;
    private float randomDirectionModifier;
    private boolean randomSpriteMirroringModifier;
    private Image lightEffect = new Image("assets/weaponSprites/bulletFireLight.png");

    
    //Konstruktor

    public BulletFireAnimation(float centerX, float centerY, float direction, float offsetX, float offsetY)
            throws SlickException {
        super("assets/weaponSprites/bulletFire.png", centerX, centerY, direction);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.random = new Random();
    }
    

    //Methoden

    public void animate() {
        //Animationstimer starten
        this.animationTimer = this.animationTime;

        //Zufällig eine leichte Abweichung der Richtung festlegen, um nicht immer gleich auszusehen
        this.randomSpriteMirroringModifier = random.nextBoolean();

        //Zufällig entscheiden, ob das Sprite gespiegelt werden soll
        this.randomDirectionModifier = (random.nextFloat() - 0.5f) * 27;
    }
    
    public void update() {
        //Animationstimer laufen lassen, falls noch nicht beendet
        if (animationTimer > 0) {
            animationTimer--;
        }
    }

    public void render(Graphics g) {
        //Prüfen, ob das Schussfeuer dargestellt werden soll
        if (animationTimer == 0) {
            return;
        }

        //Sprite auswählen
        Image image = randomSpriteMirroringModifier ? this.sprite.getFlippedCopy(false, true) : this.sprite;

        //Die Position des Schussfeuers lesen
        float bulletFireX = this.shape.getX();
        float bulletFireY = this.shape.getY();

        //Den rotierten Offset berechnen (um die Waffe in der Hand darzustellen)
        float rotatedOffsetX = (float) (this.offsetX * Math.cos(Math.toRadians(direction))
                - this.offsetY * Math.sin(Math.toRadians(direction)));
        float rotatedOffsetY = (float) (this.offsetX * Math.sin(Math.toRadians(direction))
                + this.offsetY * Math.cos(Math.toRadians(direction)));

        //Den rotierten Offset zu den Waffenkoordinaten addieren
        float x = bulletFireX + rotatedOffsetX;
        float y = bulletFireY + rotatedOffsetY;

        //Rotation hinzufügen
        image.setRotation(direction + randomDirectionModifier);

        //Lichteffekt rendern
        lightEffect.draw(x, y);

        //Sprite rendern
        image.draw(x, y);

    }
}
