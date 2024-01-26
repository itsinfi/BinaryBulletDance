package Entities.Animations;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Entities.Animatable;
import Entities.Entity;
import Entities.Renderable;

/**
 * Diese Klasse stellt das Hinzufügen von Schaden bei einer lebendigen Entität visuell dar.
 */
public class DamageAnimation extends Entity implements Animatable, Renderable {

    short animationTime = 20;
    short animationTimer;


    /**
     * Diese Methode erzeugt eine Schadensanimation
     * 
     * @param spriteAsset Pfad zum Asset des Sprites der lebendigen Enität
     * @param centerX Zentrale x-Koordinate der lebendigen Entität
     * @param centerY Zentrale y-Koordinate der lebendigen Entität
     * @param direction Richtung der lebendigen Entität
     * @throws SlickException Falls ein Fehler beim Erzeugen auftritt
     */
    public DamageAnimation(String spriteAsset, float centerX, float centerY, float direction) throws SlickException {
        super(spriteAsset, centerX, centerY, direction);
    }


    /**
     * Diese Methode startet den Animationstimer.
     */
    @Override
    public void animate() {
        animationTimer = animationTime;
    }

    /**
     * Diese Methode updatet den Animationstimer.
     */
    @Override
    public void update() {
        if (animationTimer > 0) {
            animationTimer--;
        }
    }

    /**
     * Diese Methode stellt den Schaden visuell dar.
     * 
     * @param g Grafische Darstellung des Spiels durch die Slick2D-Library
     */
    @Override
    public void render(Graphics g) {
        if (animationTimer > 0) {

            //Transparenz der Farbe wird auf den 10-fachen Wert des Animationstimers gesetzt (Fade-Out-Effekt)
            Color damageColor = new Color(255, 0, 0, animationTimer * 10);

            //Rendern
            float x = this.shape.getX();
            float y = this.shape.getY();
            this.sprite.setRotation(direction);
            this.sprite.draw(x, y, damageColor);
        }
    }
    
}
