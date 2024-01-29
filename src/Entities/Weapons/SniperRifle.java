package Entities.Weapons;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import Controllers.WeaponController;
import Entities.LivingEntity;
import Entities.Weapon;

/**
 * Scharfschützengewehr mit hoher Reichweite, viel Schaden aber geringer Feuerrate
 * 
 * @author Sascha Angermann
 */
public class SniperRifle extends Weapon {


    //Attribute


    //Konstruktoren

    /**
     * Diese Methode erstellt eine SniperRifle
     */
    public SniperRifle(LivingEntity livingEntity) throws SlickException {

        //SniperRifle  erzeugen
        super("assets/weaponSprites/sniperRifle.png", livingEntity.getShape().getCenterX(), livingEntity.getShape().getCenterY(),
                livingEntity.getDirection(), false, (short) 500, "SNIPER_RIFLE", (short) 120, 1f, (short) 3000, (short) 5,
                (short) 240, false, 25f, 24f, 60f, 15f,
                new Sound("assets/sounds/sniper_rifle_shoot.wav"));

        //SniperRifle dem WeaponController übergeben
        WeaponController.addWeapon((Weapon) this);
    }


    //Getter


    //Setter


    //Methoden

    
}
