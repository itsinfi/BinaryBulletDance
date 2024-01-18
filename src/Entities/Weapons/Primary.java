package Entities.Weapons;

import Controllers.WeaponController;
import Entities.Weapon;

/**
 * Die Primärwaffe.
 * 
 * @author Sascha Angermann
 */
public class Primary extends Weapon {

    public Primary() {
        //Primärwaffe  erzeugen
        super(false, (short) 30, "PRIMARY", (short) 5, 0.0, (short) 200, (short) 30,
                (short) 30, (short) 120, true);

        //Primärwaffe dem WeaponController übergeben
        WeaponController.addWeapon((Weapon) this);
    }


    
    
}
