package Entities;

import java.util.HashMap;

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

    private Weapon weaponSlot1;
    private Weapon weaponSlot2;
    private Weapon weaponSlot3;
    private Weapon weaponSlot4;
    private Weapon weaponSlot5;
    private HashMap<String, Short> ammo = new HashMap<String, Short>();
    private short changeEquippedWeaponTimer = 0;


    //Konstruktoren

    /**
     * Diese Klasse erstellt und initialisiert den Spieler und dessen Attribute.
     * 
     * @param playerAsset Pfad zum Image-Asset zur Darstellung der Entität
     * @param container container of the window
     * @throws SlickException falls etwas bei der Erstellung des Sprites oder Shapes nicht klappt.
     */
    public Player(String playerAsset, GameContainer container) throws SlickException {
        
        //Spieler in der Mitte des Bildschirms spawnen
//        super(playerAsset, container.getWidth() / 2, container.getHeight() / 2, 0);
        
        // Spieler auf custom spawnpoint spawnen
        super(playerAsset, 2624, 2304, 0);

        //Werte festlegen
        this.hitpoints = 1000;
        this.maxHitpoints = 1000;
        this.movementSpeed = 0.4f;
    }
    

    //Getter

    /**
     * Diese Methode gibt die Waffe des Waffenslots zurück
     * 
     * @param Waffenslotnummer
     * 
     * @return Waffe im Waffenslot
     */
    public Weapon getWeaponSlot(short slot) {
        switch (slot) {
            case 1:
                return weaponSlot1;
            case 2:
                return weaponSlot2;
            case 3:
                return weaponSlot3;
            case 4:
                return weaponSlot4;
            case 5:
                return weaponSlot5;
            default:
                throw new IllegalArgumentException("Weapon Slot out of reach");
        }
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
     * @param slot Waffenslotnummer
     */
    public void setEquippedWeapon(short slot) {
        switch (slot) {
            case 1:
                this.equippedWeapon = this.weaponSlot1;
                break;
            case 2:
                this.equippedWeapon = this.weaponSlot2;
                break;
            case 3:
                this.equippedWeapon = this.weaponSlot3;
                break;  
            case 4:
                this.equippedWeapon = this.weaponSlot4;
                break;
            case 5:
                this.equippedWeapon = this.weaponSlot5;
                break;
            default:
                return;
        }
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
    public void addWeapon(Weapon weapon, short slot) {
        switch (slot) {
            case 1:
                this.weaponSlot1 = weapon;
                break;
            case 2:
                this.weaponSlot2 = weapon;
                break;
            case 3:
                this.weaponSlot3 = weapon;
                break;
            case 4:
                this.weaponSlot4 = weapon;
                break;
            case 5:
                this.weaponSlot5 = weapon;
                break;
            default:
                throw new IllegalArgumentException("Weapon Slot out of reach");
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
        if (this.equippedWeapon != null) {
            this.getEquippedWeapon().render();
        }
        float x = this.shape.getX();
        float y = this.shape.getY();
        this.sprite.setRotation(direction);
        this.sprite.draw(x, y);
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
    }

}
