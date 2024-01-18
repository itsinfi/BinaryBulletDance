package Entities.Weapons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import Controllers.WeaponController;
import Entities.LivingEntity;
import Entities.Weapon;

/**
 * Die Sekundärwaffe. (Pistole mit unendlich Munition)
 * 
 * @author Sascha Angermann
 */
public class Secondary extends Weapon {

    //Attribute


    //Konstruktoren

    /**
     * Diese Methode erstellt eine Sekundärwaffe
     */
    public Secondary(LivingEntity livingEntity) throws SlickException {

        //Sekundärwaffe erzeugen
        super("assets/demoSecondary.png", livingEntity.getShape().getCenterX(), livingEntity.getShape().getCenterY(), livingEntity.getDirection(),
                true, (short) 30, "SECONDARY", (short) 15, 0.0, (short) 200, (short) 15,
                (short) 15, (short) 60, false);

        //Einstellen, dass Munition für die Waffe unendlich sein soll.
        this.hasInfiniteAmmo = true;

        //Sekundärwaffe dem WeaponController übergeben
        WeaponController.addWeapon(this);
    }


    //Getter


    //Setter


    //Methoden

    
}
