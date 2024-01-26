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
     * Diese Methode erstellt eine Sekundärwaffe
     */
    public Pistol(LivingEntity livingEntity) throws SlickException {

        //Sekundärwaffe erzeugen
        super("assets/weaponSprites/pistol.png", livingEntity.getShape().getCenterX(), livingEntity.getShape().getCenterY(),
                livingEntity.getDirection(), true, (short) 30, "SECONDARY", (short) 15, 200f,
                (short) 1000, (short) 15, (short) 60, false, 10f, 26f, 36f, 30f,
                new Sound("assets/sounds/secondary_shoot.wav"), new Sound("assets/sounds/player_heal.wav"));

        //Einstellen, dass Munition für die Waffe unendlich sein soll.
        this.hasInfiniteAmmo = true;

        //Sekundärwaffe dem WeaponController übergeben
        WeaponController.addWeapon(this);
    }


    //Getter


    //Setter


    //Methoden

    
}
