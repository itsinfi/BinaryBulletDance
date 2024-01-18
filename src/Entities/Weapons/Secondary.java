package Entities.Weapons;

import Controllers.WeaponController;
import Entities.Weapon;

public class Secondary extends Weapon {

    public Secondary() {
        //Sekundärwaffe erzeugen
        super(true, (short) 30, "SECONDARY", (short) 15, 0.0, (short) 200, (short) 15,
                (short) 15, (short) 60, false);

        //Munition als unendlich einstellen
        this.hasInfiniteAmmo = true;

        //Sekundärwaffe dem WeaponController übergeben
        WeaponController.addWeapon(this);
    }
}
