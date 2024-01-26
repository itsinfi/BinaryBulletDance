package Entities.Weapons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import Controllers.WeaponController;
import Entities.LivingEntity;
import Entities.Weapon;

/**
 * Die Sekundärwaffe. (Pistole mit unendlich Munition)
 * 
 * @author Sascha Angermann
 */
public class Pistol extends Weapon {

    //Attribute


    //Konstruktoren

    /**
     * Diese Methode erstellt eine Pistol
     */
    public Pistol(LivingEntity livingEntity) throws SlickException {

        //Pistol erzeugen
        super("assets/weaponSprites/pistol.png", livingEntity.getShape().getCenterX(), livingEntity.getShape().getCenterY(),
                livingEntity.getDirection(), true, (short) 30, "PISTOL", (short) 15, 100f,
                (short) 500, (short) 15, (short) 15, (short) 60, false, 5f, 13f, 18f, 15f,
                new Sound("assets/sounds/pistol.wav"), new Sound("assets/sounds/player_heal.wav"));

        //Einstellen, dass Munition für die Waffe unendlich sein soll.
        this.hasInfiniteAmmo = true;

        //Pistol dem WeaponController übergeben
        WeaponController.addWeapon(this);
    }


    //Getter


    //Setter


    //Methoden

    
}
