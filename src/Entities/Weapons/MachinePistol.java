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
                livingEntity.getDirection(), (short) 20, "MACHINE_PISTOL", (short) 3, 500f, (short) 1000, (short) 25,
                (short) 50, true, 20f, 24f, 28f, 8f,
                new Sound("assets/sounds/machine_pistol_shoot.wav"));

        //MachinePistol dem WeaponController übergeben
        WeaponController.addWeapon((Weapon) this);
    }


    //Getter


    //Setter


    //Methoden

    
}
