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
        super("assets/demoPrimary.png", livingEntity.getShape().getCenterX(), livingEntity.getShape().getCenterY(),
                livingEntity.getShape().getWidth(), livingEntity.getShape().getHeight(), livingEntity.getDirection(),
                false, (short) 30, "PRIMARY", (short) 5, 0.0, (short) 200, (short) 30,
                (short) 30, (short) 120, true);

        //Primärwaffe dem WeaponController übergeben
        WeaponController.addWeapon((Weapon) this);
    }
    
    public Primary(GameContainer container) throws SlickException{
        //TODO: remove this constructor

        //Sekundärwaffe erzeugen
        super("assets/demoPrimary.png", container.getWidth() / 2 - 16, container.getHeight() / 2 - 16, 32, 32, 0,
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
