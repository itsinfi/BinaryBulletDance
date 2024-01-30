package Entities.Weapons;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import Controllers.WeaponController;
import Entities.LivingEntity;
import Entities.Weapon;

/**
 * Das Sturmgewehr. (Automatisch mit begrenzter Munition)
 * 
 * @author Sascha Angermann
 */
public class AssaultRifle extends Weapon {


    //Attribute


    //Konstruktoren

    /**
     * Diese Methode erstellt ein Sturmgewehr
     */
    public AssaultRifle(LivingEntity livingEntity) throws SlickException {

        //Sturmgewehr  erzeugen
        super("assets/weaponSprites/assaultRifle.png", livingEntity.getShape().getCenterX(), livingEntity.getShape().getCenterY(),
                livingEntity.getDirection(), (short) 30, "ASSAULT_RIFLE", (short) 5, 350f, (short) 1400, (short) 30,
                (short) 120, true, 35f, 24f, 60f, 7f,
                new Sound("assets/sounds/assault_rifle_shoot.wav"));

        //Sturmgewehr dem WeaponController übergeben
        WeaponController.addWeapon((Weapon) this);
    }


    //Getter


    //Setter


    //Methoden

    
}
