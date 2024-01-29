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

    private float spread = 80.0f;
    private short spreadAmount = 20;


    //Attribute


    //Konstruktoren

    /**
     * Diese Methode erstellt eine Shotgun
     */
    public Shotgun(LivingEntity livingEntity) throws SlickException {

        //Shotgun erzeugen
        super("assets/weaponSprites/shotgun.png", livingEntity.getShape().getCenterX(), livingEntity.getShape().getCenterY(),
                livingEntity.getDirection(), false, (short) 200, "SHOTGUN", (short) 30, 3f, (short) 200, (short) 8,
                (short) 120, false, 28f, 26f, 68f, 32f,
                new Sound("assets/sounds/assault_rifle_shoot.wav"), new Sound("assets/sounds/player_heal.wav"));

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
