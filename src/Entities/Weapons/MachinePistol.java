package Entities.Weapons;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import Controllers.WeaponController;
import Entities.LivingEntity;
import Entities.Weapon;

/**
 * Automatische Maschinenpistole mit begrenzter Munition (weniger Schaden und höhere Feuerrate als Assault Rifle)
 * 
 * @author Sascha Angermann
 */
public class MachinePistol extends Weapon {


    //Attribute


    //Konstruktoren

    /**
     * Diese Methode erstellt eine MachinePistol
     */
    public MachinePistol(LivingEntity livingEntity) throws SlickException {

        //MachinePistol erzeugen
        super("assets/weaponSprites/machinePistol.png", livingEntity.getShape().getCenterX(), livingEntity.getShape().getCenterY(),
                livingEntity.getDirection(), false, (short) 20, "MACHINE_PISTOL", (short) 3, 250f, (short) 500, (short) 25,
                (short) 25, (short) 50, true, 5f, 12f, 15f, 15f,
                new Sound("assets/sounds/machine_pistol_shoot.wav"), new Sound("assets/sounds/player_heal.wav"));

        //MachinePistol dem WeaponController übergeben
        WeaponController.addWeapon((Weapon) this);
    }


    //Getter


    //Setter


    //Methoden

    
}
