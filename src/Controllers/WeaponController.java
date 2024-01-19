package Controllers;

import java.util.HashSet;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import Entities.Bullet;
import Entities.LivingEntity;
import Entities.Weapon;

/**
 * Diese Klasse verwaltet alle Waffen im Game.
 * 
 * @author Sascha Angermann
 */
//TODO: ANNOTATIONEN!!!
public class WeaponController {

    //Attribute

    // private static HashSet<LivingEntity> livingEntities;
    private static HashSet<Bullet> bullets = new HashSet<Bullet>();//TODO: Zu Weapon Class ersetzen
    private static HashSet<Weapon> weapons = new HashSet<Weapon>();//TODO:

    //Konstruktoren

    //TODO: Konstruktor updaten + Annotationen hinzufügen
    // public WeaponController(HashSet<LivingEntity> livingEntities) {
    //     this.livingEntities = livingEntities;
    // }

    
    //Getter
    

    //Setter
    

    //Methoden

    public static void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public static void removeWeapon(Weapon weapon) {
        weapons.remove(weapon);
    }

    //TODO: Methoden updaten nach UML + Annotationen hinzufügen
    public static void update(Input input, float bulletSpeed, int delta, GameContainer container,
            LivingEntity livingEntity) {

        // Update bullet position and check for collisions
        Iterator<Bullet> _it = bullets.iterator();
        while (_it.hasNext()) {
            Bullet bullet = _it.next();
            if (bullet.getIsShooting()) {
                bullet.updateBullet(container, bulletSpeed, delta);
            } else {
                _it.remove();//ALTER LIVE SAVER SUPER WICHTIG MERKEN YO
            }
        }

        //Alle Waffen durchiterieren
        Iterator<Weapon> it = weapons.iterator();
        while (it.hasNext()) {
            Weapon weapon = it.next();

            //ReloadTimer aktualisieren
            if (weapon.getReloadRate() > 0 && weapon.getReloadTimer() > 0) {
                short reloadTimer = weapon.getReloadTimer();
                weapon.setReloadTimer(--reloadTimer);
            }

            //FireTimer aktualisieren
            if (weapon.getFirerate() > 0 && weapon.getFireTimer() > 0) {
                short fireTimer = weapon.getFireTimer();
                weapon.setFireTimer(--fireTimer);
            }

            //TODO: Entscheidung: Brauchen wir das? Oder ist es too much?
            if (weapon.getFireTimer() < 0) {
                weapon.setFireTimer((short) 0);
            }
            
            //TODO: Entscheidung: Brauchen wir das? Oder ist es too much?
            if (weapon.getReloadTimer() < 0) {
                weapon.setReloadTimer((short) 0);
            }
            
            //Schussfeueranimation updaten
            weapon.getBulletFire().update();
        } 
    }

    public static void render(Graphics g) {
        Iterator<Bullet> _it = bullets.iterator();
        while (_it.hasNext()) {
            try {
                _it.next().drawBullet(g);
            } catch (SlickException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //Schussfeueranimation von jeder Waffe aktualisieren
        Iterator<Weapon> it = weapons.iterator();
        while (it.hasNext()) {
            it.next().getBulletFire().render();
        }
    }

    //TODO: alles vernünftig überarbeiten
    public static void shoot(Input input, Weapon weapon) {
        weapon.attack();

        float mouseX = input.getMouseX();
        float mouseY = input.getMouseY();

        float direction = weapon.getDirection();
        float offsetX = weapon.getOffsetX() + (weapon.getAmmoType().equals("PRIMARY") ? 20 : 0);
        float offsetY = weapon.getOffsetY() + (weapon.getAmmoType().equals("PRIMARY") ? 5 : 0);

        //Die Position der Waffe lesen
        float weaponX = weapon.getShape().getCenterX();
        float weaponY = weapon.getShape().getCenterY();

        //Den rotierten Offset berechnen (um die Waffe in der Hand darzustellen)
        float rotatedOffsetX = (float) (offsetX * Math.cos(Math.toRadians(direction))
                - offsetY * Math.sin(Math.toRadians(direction)));
        float rotatedOffsetY = (float) (offsetX * Math.sin(Math.toRadians(direction))
                + offsetY * Math.cos(Math.toRadians(direction)));

        
        //Den rotierten Offset zu den Waffenkoordinaten addieren
        float x = weaponX + rotatedOffsetX;
        float y = weaponY + rotatedOffsetY;


        float bulletX = x;
        float bulletY = y;
        Vector2f bulletDirection = new Vector2f(mouseX - bulletX, mouseY - bulletY).normalise();
        Bullet bullet = new Bullet(bulletDirection, bulletX, bulletY);
        bullets.add(bullet);

        //FireTimer setzen
        weapon.setFireTimer(weapon.getFirerate());
    }

}
