package Entities.Weapons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import Controllers.WeaponController;
import Entities.LivingEntity;
import Entities.Weapon;

/**
 * Die Primärwaffe. (Automatisches Sturmgewehr mit begrenzter Munition)
 * 
 * @author Sascha Angermann
 */
public class Primary extends Weapon {


    //Attribute


    //Konstruktoren

    /**
     * Diese Methode erstellt eine Primärwaffe
     */
    public Primary(LivingEntity livingEntity) throws SlickException {

        //Primärwaffe  erzeugen
        super("assets/demoPrimary.png", livingEntity.getShape().getCenterX(), livingEntity.getShape().getCenterY(), livingEntity.getDirection(),
                false, (short) 30, "PRIMARY", (short) 5, 0.0, (short) 200, (short) 30,
                (short) 30, (short) 120, true);

        //Primärwaffe dem WeaponController übergeben
        WeaponController.addWeapon((Weapon) this);
    }


    //Getter


    //Setter


    //Methoden

    
}
