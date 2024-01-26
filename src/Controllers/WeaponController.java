package Controllers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

import Entities.LivingEntity;
import Entities.Weapon;
import Entities.Animations.BulletTraceAnimation;

/**
 * Diese Klasse verwaltet alle Waffen im Game.
 * 
 * @author Sascha Angermann
 */
//TODO: ANNOTATIONEN!!!
public abstract class WeaponController {

    //Attribute

    private static HashSet<BulletTraceAnimation> bulletTraces = new HashSet<BulletTraceAnimation>();
    private static HashSet<Weapon> weapons = new HashSet<Weapon>();//TODO:
    private static Random shotAccuracyRandomizer = new Random();

    //Konstruktoren

    
    //Getter

    /**
     * Diese Methode gibt alle aktuell im Spiel vorhandenen Waffen aus
     * 
     * @return Liste aller im Spiel vorhandenen Waffen
     */
    public static HashSet<Weapon> getWeapons() {
        return weapons;
    }
    

    //Setter
    

    //Methoden

    /**
     * Diese Methode fügt eine Waffe in die Liste aller Waffen hinzu
     * 
     * @param weapon Waffe, welche hinzugefügt werden soll
     */
    public static void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    /**
     * Diese Methode entfernt eine Waffe aus der Liste aller Waffen
     * 
     * @param weapon Waffe, welche entfernt werden soll
     */
    public static void removeWeapon(Weapon weapon) {
        weapons.remove(weapon);
    }

    /**
     * Diese Methode updated alle Waffen und ihre zugehörigen Animationen
     */
    public static void update() {

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

            //Schussfeueranimation updaten
            weapon.getBulletFire().update();
        }

        //TODO: Kommentar
        Iterator<BulletTraceAnimation> btaIterator = bulletTraces.iterator();
        while (btaIterator.hasNext()) {
            BulletTraceAnimation bulletTraceAnimation = btaIterator.next();

            if (bulletTraceAnimation.getAnimationTimer() > 0) {
                bulletTraceAnimation.update();
            } else {
                btaIterator.remove();
            }
        }
    }

    /**
     * Diese Methode rendert alle Waffen und ihre zugehörigen Animationen
     * 
     * @param g Grafische Darstellung des Spiels durch die Slick2D-Library
     */
    public static void render(Graphics g) {

        //Schussfeueranimation von jeder Waffe aktualisieren
        Iterator<Weapon> it = weapons.iterator();
        while (it.hasNext()) {
            it.next().getBulletFire().render(g);
        }

        Iterator<BulletTraceAnimation> btaIterator = bulletTraces.iterator();
        while (btaIterator.hasNext()) {
            BulletTraceAnimation bulletTraceAnimation = btaIterator.next();

            if (bulletTraceAnimation.getAnimationTimer() > 0) {
                bulletTraceAnimation.render(g);
            }
        }
    }

    //TODO: Aufräumen
    /**
     * Diese Methode führt alle nötigen Operationen durch, um eine Waffe zu schießen
     * 
     * @param livingEntity
     */
    public static void shoot(LivingEntity livingEntity) {

        //Waffe auslesen
        Weapon weapon = livingEntity.getEquippedWeapon();

        //Prüfen, ob überhaupt eine Waffe ausgerüstet ist
        if (weapon == null) {
            System.out.println("Keine Waffe zum Schießen gefunden.");
            return;
        }

        //Prüfen, ob die Waffe noch nachgeladen wird
        if (weapon.getReloadTimer() != 0) {
            System.out.println("Waffe wird noch nachgeladen.");
            return;
        }

        //Prüfen, ob die Waffe bereits geschossen werden kann (Feuerrate)
        if (weapon.getFireTimer() != 0) {
            System.out.println("Waffe ist noch nicht bereit, geschossen zu werden.");
            return;
        }

        //Prüfen, ob die Munition leer ist
        if (weapon.getBullets() <= 0) {
            System.out.println("Munition ist leer.");
            return;
        }

        //Zufällige Werte für die Punktgenauigkeit des Schusses losen
        float randomAccuracyX = (shotAccuracyRandomizer.nextFloat() - 0.5f) * weapon.getAccuracy();
        float randomAccuracyY = (shotAccuracyRandomizer.nextFloat() - 0.5f) * weapon.getAccuracy();

        //Richtung und Offset zum Spieler für den Schuss bestimen
        float direction = livingEntity.getDirection();
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
        Vector2f livingEntityDirection = new Vector2f(direction);

        //Distanz des Richtungsvektors auf die Reichweite der Waffe skalieren
        Vector2f bulletDirection = livingEntityDirection.scale(weapon.getRange());

        //Zu den Koordinaten die zufällige Treffergenauigkeit addieren
        bulletDirection.x += randomAccuracyX;
        bulletDirection.y += randomAccuracyY;

        //Vektor normalisieren (Länge auf 1 setzen)
        bulletDirection.normalise();

        //Linie für die Laufbahn erzeugen
        float lineX = (float) (weapon.getRange() * Math.cos(Math.toRadians(direction))) + randomAccuracyX;
        float lineY = (float) (weapon.getRange() * Math.sin(Math.toRadians(direction))) + randomAccuracyY;
        Line bulletLine = new Line(x, y, livingEntityX + lineX, livingEntityY + lineY);

        //Waffe schießen
        weapon.attack();

        //FireTimer setzen (Feuerrate der Waffe)
        weapon.setFireTimer(weapon.getFirerate());

        //Hitscan durchführen
        hitScan(weapon, bulletLine, livingEntity);

        //Bullet Trace Animation erzeugen und der Liste hinzufügen
        BulletTraceAnimation bulletTraceAnimation = new BulletTraceAnimation(bulletLine);
        bulletTraces.add(bulletTraceAnimation);
    }
    
    /**
     * Diese Methode berechnet, ob und wenn ja, welches Objekt die Kugellaufbahn (als Erstes) berührt
     * 
     * @param weapon Waffe, mit der geschossen wurde
     * @param bulletLine Linie der Schusslaufbahn
     */
    private static void hitScan(Weapon weapon, Line bulletLine, LivingEntity shootingEntity) {

        //Alle lebendigen Entitäten in einem HashSet abspeichern
        HashSet<LivingEntity> livingEntities = new HashSet<LivingEntity>();
        livingEntities.add(PlayerController.getPlayer());
        livingEntities.addAll(EnemyController.getEnemies());

        //Variablen setzen, um hier das nächste Objekt zu speichern, das getroffen wurde (falls eines existiert)
        LivingEntity nearestLivingEntity = null;

        //Distanz zum Startpunkt des Schusses speichern, um zu vergleichen, welches Objekt das Nächste ist.
        float nearestDistance = Float.MAX_VALUE;

        //Alle lebendigen Entitäten iterieren, um zu bestimmen, welches getroffen wurde
        for (LivingEntity livingEntity : livingEntities) {
            
            //(schießende Entität hierzu ausschließen, weil Selbstmord ist uncool)
            if (livingEntity.equals(shootingEntity)) {
                continue;
            }
            
            //Überprüfung der Distanz und als nächste Entität setzen
            float distance = bulletLine.getStart().distance(new Vector2f(livingEntity.getShape().getCenterX(),
                    livingEntity.getShape().getCenterY()));
            if (livingEntity.getShape().intersects(bulletLine) && distance < nearestDistance) {
                nearestDistance = distance;
                nearestLivingEntity = livingEntity;
            }
        }

        //Der nächsten lebendigen Entität, die auf der Kugellaufbahn lag und getroffen wurde, Schaden geben
        if (nearestLivingEntity != null) {
            nearestLivingEntity.takeDamage(weapon.getDamagePerBullet());
        }
    }

}
