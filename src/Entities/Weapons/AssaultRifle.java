package Entities.Weapons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import Controllers.WeaponController;
import Entities.LivingEntity;
import Entities.Weapon;

/**
 * Automatisches Sturmgewehr mit begrenzter Munition
 * 
 * @author Sascha Angermann
 */
public class AssaultRifle extends Weapon {


    //Attribute


    //Konstruktoren

    /**
     * Diese Methode erstellt eine AssaultRifle
     */
    public AssaultRifle(LivingEntity livingEntity) throws SlickException {

        //AssaultRifle  erzeugen
        super("assets/weaponSprites/assaultRifle.png", livingEntity.getShape().getCenterX(), livingEntity.getShape().getCenterY(),
                livingEntity.getDirection(), false, (short) 30, "ASSAULT_RIFLE", (short) 5, 200f, (short) 600, (short) 30,
                (short) 30, (short) 120, true, 20f, 12f, 50f, 15f,
                new Sound("assets/sounds/assault_rifle.wav"), new Sound("assets/sounds/player_heal.wav"));

        //AssaultRifle dem WeaponController übergeben
        WeaponController.addWeapon((Weapon) this);
    }


    //Getter


    //Setter


    //Methoden

    
}
