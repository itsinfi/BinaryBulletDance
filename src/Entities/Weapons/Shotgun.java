package Entities.Weapons;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import Controllers.WeaponController;
import Entities.LivingEntity;
import Entities.Weapon;

/**
 * Sawed-off Shotgun mit ordentlich Schaden und Spread, aber kaum Reichweite
 * 
 * @author Sascha Angermann
 */
public class Shotgun extends Weapon {

    private float spread = 100.0f;
    private short spreadAmount = 30;


    //Attribute


    //Konstruktoren

    /**
     * Diese Methode erstellt eine Shotgun
     */
    public Shotgun(LivingEntity livingEntity) throws SlickException {

        //Shotgun erzeugen
        super("assets/weaponSprites/shotgun.png", livingEntity.getShape().getCenterX(), livingEntity.getShape().getCenterY(),
                livingEntity.getDirection(), (short) 30, "SHOTGUN", (short) 60, 1f, (short) 500, (short) 8,
                (short) 120, false, 28f, 26f, 45f, 5f,
                new Sound("assets/sounds/assault_rifle_shoot.wav"));

        //Shotgun dem WeaponController übergeben
        WeaponController.addWeapon((Weapon) this);
    }


    //Getter

    /**
     * Diese Methode gibt den Grad der Splitterung des Schusses der Shotgun zurück
     * 
     * @return Grad der Splitterung des Schusses der Shotgun
     */
    public float getSpread() {
        return spread;
    }

    /**
     * Diese Methode gibt die Anzahl der Splitterungen pro Schuss zurück
     * 
     * @return Anzahl der Splitterungen
     */
    public float getSpreadAmount() {
        return spreadAmount;
    }


    //Setter


    //Methoden
    
}
