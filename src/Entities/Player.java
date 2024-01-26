package Entities;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Diese Klasse stellt den Spieler des Games dar.
 * 
 * @author Jeremy Adam
 */
public class Player extends LivingEntity {


    //Attribute

    private Weapon primaryWeapon;
    private Weapon secondaryWeapon;
    private HashMap<String, Short> ammo = new HashMap<String, Short>();
    private short changeEquippedWeaponTimer = 0;
    private Vector2f playerDirection;


    //Konstruktoren

    /**
     * Diese Klasse erstellt und initialisiert den Spieler und dessen Attribute.
     * 
     * @param playerAsset Pfad zum Image-Asset zur Darstellung der Entität
     * @param container Zur Berechnung der Koordinaten für das "shape"-Attribut
     * @throws SlickException falls etwas bei der Erstellung des Sprites oder Shapes nicht klappt.
     */
    public Player(String playerAsset, GameContainer container) throws SlickException {
        
        //Spieler in der Mitte des Bildschirms spawnen
        super(playerAsset, container.getWidth() / 2, container.getHeight() / 2, 0);

        //Werte festlegen
        this.maxHitpoints = 100;
        this.hitpoints = 100;
        this.movementSpeed = 0.27f;
    }
    

    //Getter

    /**
     * Diese Methode gibt die Primärwaffe des Spielers zurück.
     * 
     * @return Primärwaffe des Spielers
     */
    public Weapon getPrimaryWeapon() {
        return primaryWeapon;
    }

    /**
     * Diese Methode gibt die Sekundärwaffe des Spielers zurück.
     * 
     * @return Sekundärwaffe des Spielers
     */
    public Weapon getSecondaryWeapon() {
        return secondaryWeapon;
    }

    /**
     * Diese Methode gibt die Munition des Spielers aus.
     * 
     * @return Munition des Spielers
     */
    public HashMap<String, Short> getAmmo() {
        return ammo;
    }

    /**
     * Diese Methode gibt die Anzahl an Frames aus, bis wann der Spieler seine Waffe fertig gewechselt hat.
     * 
     * @return Anzahl an Frames, bis wann der Spieler seine Waffe gewechselt hat
     */
    public short getChangeEquippedWeaponTimer() {
        return changeEquippedWeaponTimer;
    }


    //Setter

    /**
     * Diese Methode legt die aktuell ausgerüstete Waffe des Spielers fest.
     * 
     * @param isPrimary True = Primärwaffe wird ausgerüstet, False = Sekundärwaffe wird ausgerüstet.
     */
    public void setEquippedWeapon(boolean isPrimary) {
        this.equippedWeapon = isPrimary ? this.primaryWeapon : this.secondaryWeapon;
        this.changeEquippedWeaponTimer = 30;
    }

    /**
     * Diese Methode setzt eine Anzahl an Frames, bis wann der Spieler seine Waffe fertig ausgerüstet hat.
     * 
     * @return Anzahl an Frames, bis wann der Spieler seine Waffe gewechselt hat
     */
    public void setChangeEquippedWeaponTimer(short changeEquippedWeaponTimer) {
        this.changeEquippedWeaponTimer = changeEquippedWeaponTimer;
    }
    

    //Methoden

    /**
     * Diese Methode fügt dem Spieler eine neue Waffe hinzu.
     * 
     * @param weapon Neue Waffe, die ausgerüstet werden soll
     */
    public void addWeapon(Weapon weapon) {
        if (weapon.isSecondary()) {
            this.secondaryWeapon = weapon;
        } else {
            this.primaryWeapon = weapon;
        }
    }

    /**
     * Diese Methode fügt dem Spieler Munition eines bestimmten Typs hinzu.
     * 
     * @param ammo Munition des Spielers.
     */
    public void addAmmo(HashMap<String, Short> ammo) {
        for (String key : ammo.keySet())

            //Falls Munition bereits vorhanden ist: Addieren
            if (this.ammo.containsKey(key)) {
                short amount = (short) (this.ammo.get(key) + ammo.get(key));
                this.ammo.put(key, amount);

            //Ansonsten den Wert so wie er ist übernehmen
            } else {
                this.ammo.put(key, ammo.get(key));
            }
    }

    /**
     * Diese Methode stellt den Spieler visuell dar.
     * 
     * @param g Grafische Darstellung des Spiels durch die Slick2D-Library
     */
    public void render(Graphics g) {
        float x = this.shape.getX();
        float y = this.shape.getY();
        this.sprite.setRotation(direction);
        this.sprite.draw(x, y);
        this.getEquippedWeapon().render();
        this.damageAnimation.render(g);
    }

    /**
     * Diese Methode lädt die aktuell ausgerüstete Waffe des Spielers nach (falls genug Munition vorhanden ist)
     */
    @Override
    public void reload() {

        //Falls Munition unendlich ist, einfach das Magazin voll machen
        if (this.equippedWeapon.getInfiniteAmmo()) {
            this.equippedWeapon.reload((short) (this.equippedWeapon.getMagazineSize() - this.equippedWeapon.getBullets()));
        }

        //Munitionstyp der Waffe abfragen
        String ammoTypeString = this.equippedWeapon.getAmmoType();

        //Prüfen, ob der Munitionstyp im Inventar des Spielers als Eintrag vorhanden ist.
        if (!(this.ammo.containsKey(ammoTypeString))) {
            System.out.println(ammoTypeString + " konnte nicht gefunden werden.");
            return;
        }

        //Munitionsmenge aus dem Inventar herauslesen
        short ammoCount = this.ammo.get(ammoTypeString);

        //Prüfen, ob die Munition leer ist.
        if (ammoCount <= 0) {
            System.out.println("Munition ist leer.");
            return;
        }

        //Menge an nachzuladener Munition berechnen (um ein volles Magazin zu erhalten)
        short neededBullets = (short) (this.equippedWeapon.getMagazineSize() - this.equippedWeapon.getBullets());

        //Menge an Munition im Inventar des Spielers abziehen.
        if (ammoCount  >= neededBullets) {
            this.ammo.replace(ammoTypeString, (short) (ammoCount - neededBullets));
        } else {
            this.ammo.replace(ammoTypeString, (short) 0);
        }

        //Waffe nachladen
        this.equippedWeapon.reload((short) (ammoCount - this.ammo.get(ammoTypeString)));
    }

    /**
     * Diese Methode legt fest, was passieren soll, sobald der Spieler stirbt.
     */
    @Override
    public void die() {
        // TODO: Auto-generated method stub
    	
    	
//        throw new UnsupportedOperationException("Unimplemented method 'die'");
    }


	public Vector2f getPlayerDirection() {
		return playerDirection;
	}


	public void setPlayerDirection(Vector2f playerDirection) {
		this.playerDirection = playerDirection;
	}

}
