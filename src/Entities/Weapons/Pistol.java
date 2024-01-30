package Entities.Weapons;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import Controllers.WeaponController;
import Entities.LivingEntity;
import Entities.Weapon;

/**
 * Die Pistole (Pistole mit unendlich Munition)
 * 
 * @author Sascha Angermann
 */
public class Pistol extends Weapon {

    //Attribute


    //Konstruktoren

    /**
     * Diese Methode erstellt eine Pistole
     */
    public Pistol(LivingEntity livingEntity) throws SlickException {

        //Pistole erzeugen
        super("assets/weaponSprites/pistol.png", livingEntity.getShape().getCenterX(), livingEntity.getShape().getCenterY(),
                livingEntity.getDirection(), (short) 30, "PISTOL", (short) 15, 200f,
                (short) 1000, (short) 15, (short) 60, false, 25f, 26f, 26f, 5f,
                new Sound("assets/sounds/pistol_shoot.wav"));

        //Einstellen, dass Munition für die Waffe unendlich sein soll.
        this.hasInfiniteAmmo = true;

        //Pistole dem WeaponController übergeben
        WeaponController.addWeapon(this);
    }


    //Getter


    //Setter


    //Methoden

    
}
