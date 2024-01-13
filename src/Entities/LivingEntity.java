package Entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Weapons.Weapon;

/**
 * Diese Klasse stellt die Vorgabe für alle lebendigen Entitäten im Game dar, sprich Spieler und Gegner
 * 
 * @author Sascha Angermann
 */
public abstract class LivingEntity extends Entity {

    
    //Attribute

    protected Weapon equippedWeapon;
    protected short hitpoints;
    protected short maxHitpoints = 100;
    protected short invincibilityTime = 0;
    protected float movementSpeed = 0.27f;


    //Konstruktoren

    /**
     * Diese Methode erzeugt ein Objekt dieser Klasse und initialisiert seine Attribute
     * 
     * Die Attribute x, y, width und height werden hierbei verwendet, um ein Shape-Objekt zu erzeugen.
     * 
     * @param spriteAsset Pfad zum Image-Asset zur Darstellung der Entität
     * @param x x-Koordinate der Position der Entität
     * @param y y-Koordinate der Position der Entität
     * @param width Räumliche Breite der Entität
     * @param height Räumliche Höhe der Entität
     * @param direction Blickrichtung der Entität (in Grad)
     * @param equippedWeapon Aktuell ausgerüstete Waffe der Entität
     * @throws SlickException falls etwas bei der Erstellung des Sprites oder Shapes nicht klappt.
     */
    public LivingEntity(String spriteAsset, float x, float y, float width, float height, float direction, Weapon equippedWeapon) throws SlickException {
        super(spriteAsset, x, y, width, height, direction);
        this.equippedWeapon = equippedWeapon;
    }


    //Getter

    /**
     * Diese Methode gibt die aktuell ausgerüstete Waffe der Entität zurück.
     * 
     * @return Aktuell ausgerüstete Waffe der Entität
     */
    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    /**
     * Diese Methode gibt die aktuelle Anzahl an Lebenspunkten der Entität zurück.
     * 
     * @return Aktuelle Anzahl an Lebenspunkten der Entität
     */
    public short getHitpoints() {
        return hitpoints;
    }

    /**
     * Diese Methode gibt die aktuelle Anzahl an Frames, bis die Entität erneut Schaden nehmen kann, zurück.
     * 
     * @return Aktuelle Anzahl an Frames, bis die Entität erneut Schaden nehmen kann
     */
    public short getInvincibilityTime() {
        return invincibilityTime;
    }

    /**
     * Diese Methode gibt die aktuelle Bewegungsgeschwindigkeit der Entität zurück
     * 
     * @return Bewegungsgeschwindigkeit der Entität
     */
    public float getMovementSpeed() {
        return movementSpeed;
    }


    //Setter
    
    /**
     * Diese Methode legt die aktuelle Anzahl an Healthpoints fest.
     * 
     * @param hitpoints Aktuelle Anzahl an Lebenspunkten der Entität
     */
    public void setHitpoints(short hitpoints) {
        this.hitpoints = hitpoints;
    }

    /**
     * Diese Methode legt eine Anzahl an Frames fest, ab welcher die Entität erneut Schaden nehmen kann.
     * 
     * @param invincibilityTime Aktuelle Anzahl an Frames, bis die Entität erneut Schaden nehmen kann
     */
    public void setInvincibilityTime(short invincibilityTime) {
        this.invincibilityTime = invincibilityTime;
    }


    //Methoden

    //TODO: add heal and takeDamage methods

    /**
     * Diese Methode soll dafür sorgen, dass eine lebendige Entität sterben kann, um dann bestimmte Events zu Triggern.
     */
    public abstract void die();

    /**
     * Diese Methode stellt die lebendige Entität visuell dar.
     * 
     * @param g Grafische Darstellung des Spiels durch die Slick2D-Library
     */
    public abstract void render(Graphics g);

}