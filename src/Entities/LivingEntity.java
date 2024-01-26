package Entities;

import org.newdawn.slick.SlickException;

import Entities.Animations.DamageAnimation;

/**
 * Diese Klasse stellt die Vorgabe für alle lebendigen Entitäten im Game dar, sprich Spieler und Gegner
 * 
 * @author Sascha Angermann
 */
public abstract class LivingEntity extends Entity implements Renderable {

    
    //Attribute

    protected Weapon equippedWeapon;
    protected short hitpoints;
    protected short maxHitpoints = 100;
    protected short invincibilityTime = 0;
    protected float movementSpeed = 0.27f;
    protected DamageAnimation damageAnimation;


    //Konstruktoren

    /**
     * Diese Methode erzeugt ein Objekt dieser Klasse und initialisiert seine Attribute
     * 
     * Die Attribute x, y, width und height werden hierbei verwendet, um ein Shape-Objekt zu erzeugen.
     * 
     * @param spriteAsset Pfad zum Image-Asset zur Darstellung der Entität
     * @param centerX Zentrale x-Koordinate der Position der Entität
     * @param centerY Zentrale y-Koordinate der Position der Entität
     * @param direction Blickrichtung der Entität (in Grad)
     * @throws SlickException falls etwas bei der Erstellung des Sprites oder Shapes nicht klappt.
     */
    public LivingEntity(String spriteAsset, float centerX, float centerY, float direction) throws SlickException {
        super(spriteAsset, centerX, centerY, direction);
        this.damageAnimation = new DamageAnimation(spriteAsset, this.shape.getCenterX(), this.shape.getCenterY(), this.direction);
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

    /**
     * Diese Methode gibt die Schadensanimation der Entität zurück.
     * 
     * @return Schadensanimation
     */
    public DamageAnimation getDamageAnimation() {
        return damageAnimation;
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
     * Diese Methode heilt die Entität.
     * 
     * @param amount Anzahl an Healthpoints, um die die Entität geheilt wird. Der Wert muss positiv sein!
     */
    public void heal(short amount) {
    	
    	//TODO: add exception handling
    	if(amount < 0) {
    		throw new IllegalArgumentException("Negative amounts are not allowed for healing! Use takeDamage(amount) if you want to deal damage to an entity instead!");
    	}
    	
    	short finalAmount = (short) (this.getHitpoints() + amount);
    	
    	if (finalAmount > this.maxHitpoints) {
    		this.setHitpoints(this.maxHitpoints);
    	} else {
    		this.setHitpoints(finalAmount);
    	}
    	
    }
    
    /**
     * Diese Methode fügt der Entität Schaden zu.
     * @param amount Anzahl an Schadenspunkten, die zugefügt werden sollen. - Muss positiv sein!
     */
    public void takeDamage(short amount) {
    	
    	//TODO: add exception handling
    	if(amount < 0) {
    		throw new IllegalArgumentException("Negative amounts of damage are not allowed! Use heal(amount) if you want to heal an entity instead!");
    	}
    	
    	short finalAmount = (short) (this.getHitpoints() - amount);
    	
    	if(finalAmount <= 0) {
    		this.setHitpoints((short) (0));
    		this.die();
        } else {
            this.setHitpoints(finalAmount);
        }
        
        //Schadensanimation abspielen
        damageAnimation.animate();
    }

    /**
     * Diese Methode soll dafür sorgen, dass eine lebendige Entität sterben kann, um dann bestimmte Events zu Triggern.
     */
    public abstract void die();

    /**
     * Diese Methode lädt die aktuell ausgerüstete Waffe der lebendigen Entität nach.
     */
    public abstract void reload();

}