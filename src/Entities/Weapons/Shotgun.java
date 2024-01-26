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


    //Attribute


    //Konstruktoren

    /**
     * Diese Methode erstellt eine Shotgun
     */
    public Shotgun(LivingEntity livingEntity) throws SlickException {

        //Shotgun erzeugen
        super("assets/weaponSprites/shotgun.png", livingEntity.getShape().getCenterX(), livingEntity.getShape().getCenterY(),
                livingEntity.getDirection(), false, (short) 200, "SHOTGUN", (short) 60, 3f, (short) 150, (short) 8,
                (short) 8, (short) 120, false, 14f, 13f, 34f, 16f,
                new Sound("assets/sounds/assault_rifle_shoot.wav"), new Sound("assets/sounds/player_heal.wav"));

        //Shotgun dem WeaponController übergeben
        WeaponController.addWeapon((Weapon) this);
    }


    //Getter


    //Setter


    //Methoden
    
}
