package Entities.Weapons;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import Controllers.WeaponController;
import Entities.LivingEntity;
import Entities.Weapon;

/**
 * Die Primärwaffe. (Automatisches Sturmgewehr mit begrenzter Munition)
 * 
 * @author Sascha Angermann
 */
public class AssaultRifle extends Weapon {


    //Attribute


    //Konstruktoren

    /**
     * Diese Methode erstellt eine Primärwaffe
     */
    public AssaultRifle(LivingEntity livingEntity) throws SlickException {

        //Primärwaffe  erzeugen
        super("assets/weaponSprites/assaultRifle.png", livingEntity.getShape().getCenterX(), livingEntity.getShape().getCenterY(),
                livingEntity.getDirection(), false, (short) 30, "ASSAULT_RIFLE", (short) 5, 350f, (short) 1400, (short) 30,
                (short) 120, true, 40f, 24f, 100f, 30f,
                new Sound("assets/sounds/assault_rifle_shoot.wav"));

        //Primärwaffe dem WeaponController übergeben
        WeaponController.addWeapon((Weapon) this);
    }


    //Getter


    //Setter


    //Methoden

    
}
