package Controllers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
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
    private static Random shotAccuracyRandomizer = new Random();

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
                _it.remove();
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
    public static void shoot(LivingEntity livingEntity) {

        //Waffe auslesen
        Weapon weapon = livingEntity.getEquippedWeapon();

        //Prüfen, ob überhaupt eine Waffe ausgerüstet ist
        if (weapon == null) {
            System.out.println("Keine Waffe zum Schießen gefunden.");
            return;
        }

        //Waffe schießen
        weapon.attack();

        //Zufällige Werte für die Punktgenauigkeit des Schusses losen
        float randomAccuracyX = (shotAccuracyRandomizer.nextFloat() - 0.5f) * weapon.getAccuracy();
        float randomAccuracyY = (shotAccuracyRandomizer.nextFloat() - 0.5f) * weapon.getAccuracy();

        //Richtung und Offset zum Spieler für den Schuss bestimen
        float direction = weapon.getDirection();
        float offsetX = weapon.getOffsetX() + (weapon.getAmmoType().equals("PRIMARY") ? 20 : 0);
        float offsetY = weapon.getOffsetY() + (weapon.getAmmoType().equals("PRIMARY") ? 5 : 0);

        //Die Position der lebendigen Entität, die schießt, lesen
        float livingEntityX = livingEntity.getShape().getCenterX();
        float livingEntityY = livingEntity.getShape().getCenterY();

        //Den rotierten Offset berechnen (um den Punkt, wo die Waffe abgeschossen wird, zu berechnen)
        float rotatedOffsetX = (float) (offsetX * Math.cos(Math.toRadians(direction))
                - offsetY * Math.sin(Math.toRadians(direction)));
        float rotatedOffsetY = (float) (offsetX * Math.sin(Math.toRadians(direction))
                + offsetY * Math.cos(Math.toRadians(direction)));
        
        //Den rotierten Offset zu den Waffenkoordinaten addieren
        float x = livingEntityX + rotatedOffsetX;
        float y = livingEntityY + rotatedOffsetY;

        //Richtung der lebendigen Enittät auslesen und einen Vektor dazu berechnen
        Vector2f livingEntityDirection = new Vector2f(livingEntity.getDirection());

        //Distanz des Richtungsvektors auf die Reichweite der Waffe skalieren
        Vector2f bulletDirection = livingEntityDirection.scale(weapon.getRange());

        //Zu den Koordinaten die zufällige Treffergenauigkeit addieren
        bulletDirection.x += randomAccuracyX;
        bulletDirection.y += randomAccuracyY;

        //Vektor normalisieren (Länge auf 1 setzen)
        bulletDirection.normalise();

        //Linie für die Laufbahn erzeugen
        float lineX = (float) (weapon.getRange() * Math.cos(Math.toRadians(direction)));
        float lineY = (float) (weapon.getRange() * Math.sin(Math.toRadians(direction)));
        Line bulletLine = new Line(x, y, livingEntityX + lineX, livingEntityY + lineY);

        //Kugel erstellen
        Bullet bullet = new Bullet(bulletDirection, x, y, weapon.getRange());
        bullets.add(bullet);

        //FireTimer setzen (Feuerrate der Waffe)
        weapon.setFireTimer(weapon.getFirerate());
    }

}
