package Entities;

import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Weapons.Weapon;

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


    //Konstruktoren

    /**
     * Diese Klasse erstellt und initialisiert den Spieler und dessen Attribute.
     * 
     * @param playerAsset Pfad zum Image-Asset zur Darstellung der Entität
     * @param container Zur Berechnung der Koordinaten für das "shape"-Attribut
     * @param equippedWeapon Aktuell ausgerüstete Waffe der Entität
     * @param primaryWeapon Primärwaffe des Spielers
     * @param secondaryWeapon Sekundärwaffe des Spielers
     * @throws SlickException falls etwas bei der Erstellung des Sprites oder Shapes nicht klappt.
     */
    public Player(String playerAsset, GameContainer container, Weapon equippedWeapon, Weapon primaryWeapon,
            Weapon secondaryWeapon) throws SlickException {
        super(playerAsset, container.getWidth() / 2 - 16, container.getHeight() / 2 - 16, 32, 32,
                0 /*TODO: add direction*/, equippedWeapon);
        this.primaryWeapon = primaryWeapon;
        this.secondaryWeapon = secondaryWeapon;
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


    //Setter

    /**
     * Diese Methode legt die Primärwaffe des Spielers fest.
     * 
     * @param primaryWeapon Primärwaffe des Spielers
    */
    public void setPrimaryWeapon(Weapon primaryWeapon) {
        this.primaryWeapon = primaryWeapon;
    }

    /**
     * Diese Methode legt die Sekundärwaffe des Spielers fest.
     * 
     * @param secondaryWeapon Sekundärwaffe des Spielers
     */
    public void setSecondaryWeapon(Weapon secondaryWeapon) {
        this.secondaryWeapon = secondaryWeapon;
    }

    /**
     * Diese Methode legt die Munition des Spielers fest.
     * 
     * @param ammo Munition des Spielers.
     */
    public void setAmmo(HashMap<String, Short> ammo) {
        this.ammo = ammo;
    }

    /**
     * Diese Methode legt die aktuell ausgerüstete Waffe des Spielers fest.
     * 
     * @param isPrimary True = Primärwaffe wird ausgerüstet, False = Sekundärwaffe wird ausgerüstet.
     */
    public void setEquipped(boolean isPrimary) {
        this.equippedWeapon = isPrimary ? this.primaryWeapon : this.secondaryWeapon;
    }
    

    //Methoden

    /**
     * Diese Methode stellt den Spieler visuell dar.
     * 
     * @param g Grafische Darstellung des Spiels durch die Slick2D-Library
     */
    public void render(Graphics g) {
        this.sprite.draw(this.shape.getCenterX(), this.shape.getCenterY());
    }

    /**
     * Diese Methode lädt die aktuell ausgerüstete Waffe des Spielers nach (falls genug Munition vorhanden ist)
     */
    @Override
    public void reload() {
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
        throw new UnsupportedOperationException("Unimplemented method 'die'");
    }

}
