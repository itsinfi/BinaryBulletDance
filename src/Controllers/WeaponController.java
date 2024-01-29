package Controllers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;

import Entities.Enemy;
import Entities.LivingEntity;
import Entities.Player;
import Entities.Weapon;
import Entities.Animations.BulletTraceAnimation;
import Entities.Weapons.Shotgun;
import Entities.Weapons.SniperRifle;

/**
 * Diese Klasse verwaltet alle Waffen im Game.
 * 
 * @author Sascha Angermann
 */
public abstract class WeaponController {

    //Attribute

    private static HashSet<BulletTraceAnimation> bulletTraces = new HashSet<BulletTraceAnimation>();
    private static HashSet<Weapon> weapons = new HashSet<Weapon>();
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

        //Alle Schussanimationen aktualisieren (oder entfernen falls abgelaufen)
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

    /**
     * Diese Methode führt alle nötigen Prüfungen durch, bevor eine Waffe geschossen werden kann
     * 
     * @param shootingEntity Die schießende lebendige Entität
     * @param xCursor (Optional) x-Koordinate für Punkt, welche in der Schusslaufbahn enthalten sein soll (wichtig für Mauszeiger)
     * @param yCursor (Optional) y-Koordinate für Punkt, welche in der Schusslaufbahn enthalten sein soll (wichtig für Mauszeiger)
     */
    public static void shoot(LivingEntity shootingEntity, Float xCursor, Float yCursor) {

        //Waffe auslesen
        Weapon weapon = shootingEntity.getEquippedWeapon();

        //Prüfen, ob überhaupt eine Waffe ausgerüstet ist
        if (weapon == null) {
            // System.out.println("Keine Waffe zum Schießen gefunden.");
            return;
        }

        //Prüfen, ob die Waffe noch nachgeladen wird
        if (weapon.getReloadTimer() != 0) {
            // System.out.println("Waffe wird noch nachgeladen.");
            return;
        }

        //Prüfen, ob die Waffe bereits geschossen werden kann (Feuerrate)
        if (weapon.getFireTimer() != 0) {
            // System.out.println("Waffe ist noch nicht bereit, geschossen zu werden.");
            return;
        }

        //Prüfen, ob die Munition leer ist
        if (weapon.getBullets() <= 0) {
            // System.out.println("Munition ist leer.");
            return;
        }

        //Falls Waffe eine Shotgun ist, die spezielle Funktion für Shotguns aufrufen:
        if (weapon instanceof Shotgun) {
            Shotgun shotgun = (Shotgun) weapon;
            for (int i = 0; i < shotgun.getSpreadAmount(); i++) {
                short range = (short) (shotAccuracyRandomizer.nextFloat() * weapon.getRange());
                calculateShot(weapon, shootingEntity, xCursor, yCursor, shotgun.getSpread(), range);
            }
        } else {
            calculateShot(weapon, shootingEntity, xCursor, yCursor, weapon.getAccuracy(), weapon.getRange());
        }

    }
    
    /**
     * Diese Methode führt alle nötigen Berechnungen durch, um die maximale Schusslaufbahn eines Schusses zu bestimmen.
     * 
     * @param weapon Waffe, mit welcher geschossen wird
     * @param shootingEntity Die schießende lebendige Entität
     * @param xCursor (Optional) x-Koordinate für Punkt, welche in der Schusslaufbahn enthalten sein soll (wichtig für Mauszeiger)
     * @param yCursor (Optional) y-Koordinate für Punkt, welche in der Schusslaufbahn enthalten sein soll (wichtig für Mauszeiger)
     * @param accuracy Zielgenauigkeit des Schusses
     * @param range Maximale Reichweite des Schusses
     */
    public static void calculateShot(Weapon weapon, LivingEntity shootingEntity, Float xCursor, Float yCursor, float accuracy, short range) {

        //Zufällige Werte für die Punktgenauigkeit des Schusses losen
        float randomAccuracyX = (shotAccuracyRandomizer.nextFloat() - 0.5f) * accuracy;
        float randomAccuracyY = (shotAccuracyRandomizer.nextFloat() - 0.5f) * accuracy;

        //Richtung und Offset zum Spieler für den Schuss bestimen
        float direction = shootingEntity.getDirection();
        float offsetX = weapon.getOffsetX() + weapon.getBulletFireOffsetX();
        float offsetY = weapon.getOffsetY() + weapon.getBulletFireOffsetY() - 20;

        //Die Position der lebendigen Entität, die schießt, lesen
        float shootingEntityX = shootingEntity.getShape().getCenterX();
        float shootingEntityY = shootingEntity.getShape().getCenterY();

        //Den rotierten Offset berechnen (um den Punkt, wo die Waffe abgeschossen wird, zu berechnen)
        float rotatedOffsetX = (float) (offsetX * Math.cos(Math.toRadians(direction))
                - offsetY * Math.sin(Math.toRadians(direction)));
        float rotatedOffsetY = (float) (offsetX * Math.sin(Math.toRadians(direction))
                + offsetY * Math.cos(Math.toRadians(direction)));

        //Den rotierten Offset zu den Waffenkoordinaten addieren
        float x = shootingEntityX + rotatedOffsetX;
        float y = shootingEntityY + rotatedOffsetY;

        //Richtung der lebendigen Enittät auslesen und einen Vektor dazu berechnen (falls xCursor und yCursor null sind)
        Vector2f shootingEntityDirection;
        if (xCursor == null && yCursor == null) {
            shootingEntityDirection = new Vector2f(direction);

            //Position eines Punktes berechnen, und Vektor dazu berechnen (falls xCursor und yCursor nicht null sind)
        } else if (xCursor != null && yCursor != null) {
            shootingEntityDirection = new Vector2f(xCursor - x, yCursor - y);

            //Andernfalls returnen, weil es sollen beide Werte null oder nicht null sein
        } else {
            return;
        }

        //Distanz des Richtungsvektors auf die Reichweite der Waffe skalieren
        Vector2f bulletDirection = shootingEntityDirection.scale(range);

        //Zu den Koordinaten die zufällige Treffergenauigkeit addieren
        bulletDirection.x += randomAccuracyX;
        bulletDirection.y += randomAccuracyY;

        //Vektor normalisieren (Länge auf 1 setzen)
        bulletDirection.normalise();

        //Linie für die maximale Laufbahn der Kugel erzeugen
        float bulletDirectionInDegrees = (float) bulletDirection.getTheta();
        float lineX = (float) (range * Math.cos(Math.toRadians(bulletDirectionInDegrees)))
                + randomAccuracyX;
        float lineY = (float) (range * Math.sin(Math.toRadians(bulletDirectionInDegrees)))
                + randomAccuracyY;
        Line bulletLine = new Line(x, y, x + lineX, y + lineY);

        //Waffe schießen
        weapon.attack();

        //FireTimer setzen (Feuerrate der Waffe)
        weapon.setFireTimer(weapon.getFirerate());

        //Hitscan durchführen
        if (weapon instanceof SniperRifle) {
            hitScanSniperRifle(weapon, bulletLine, shootingEntity);
        } else {
            hitScan(weapon, bulletLine, shootingEntity);
        }

    }
    
    /**
     * Diese Methode berechnet, ob und wenn ja, welches Objekt die Kugellaufbahn (als Erstes) berührt
     * 
     * @param weapon Waffe, mit der geschossen wurde
     * @param bulletLine Linie der Schusslaufbahn
     * @param shootingEntity Schießende Entität (soll sich selbst keinen Schaden zufügen können)
     */
    private static void hitScan(Weapon weapon, Line bulletLine, LivingEntity shootingEntity) {

        //Alle lebendigen Entitäten in einem HashSet abspeichern
        HashSet<LivingEntity> livingEntities = new HashSet<LivingEntity>();
        livingEntities.add(PlayerController.getPlayer());
        livingEntities.addAll(EnemyController.getEnemies());

        //Bestimmen, ob die schießende Entität ein Gegner ist
        boolean entityIsEnemy = shootingEntity instanceof Enemy;

        //Variablen setzen, um hier das nächste Objekt zu speichern, das getroffen wurde (falls eines existiert)
        LivingEntity nearestLivingEntity = null;

        //Distanz zum Startpunkt des Schusses speichern, um zu vergleichen, welches Objekt das Nächste ist.
        float nearestDistance = Float.MAX_VALUE;

        //Alle lebendigen Entitäten iterieren, um zu bestimmen, welche evtl. getroffen wurde
        for (LivingEntity livingEntity : livingEntities) {

            //(schießende Entität hierzu ausschließen, weil Selbstmord ist uncool)
            if (livingEntity.equals(shootingEntity)) {
                continue;
            }

            //Überprüfung auf Überschneidung und falls ja, Abspeichern der Distanz und der Entität
            float distance = bulletLine.getStart().distance(new Vector2f(livingEntity.getShape().getCenterX(),
                    livingEntity.getShape().getCenterY()));
            if (livingEntity.getShape().intersects(bulletLine) && distance < nearestDistance) {

                //Kugelabprall durch neuen Distanzwert sicherstellen
                nearestDistance = distance;

                //Überprüfung auf Friendly Fire (falls ja, soll kein Schaden gegeben werden)
                if (!entityIsEnemy || entityIsEnemy && livingEntity instanceof Player) {
                    nearestLivingEntity = livingEntity;
                } else {
                    nearestLivingEntity = null;
                }
            }
        }

        //Der nächsten lebendigen Entität, die auf der Kugellaufbahn lag und getroffen wurde, Schaden geben
        if (nearestLivingEntity != null) {
            nearestLivingEntity.takeDamage(weapon.getDamagePerBullet());

            //Schussbahn um die Distanz zum Aufprallpunkt verkürzen, falls ein Objekt getroffen wurde
            double ratio = nearestDistance / bulletLine.length();
            float newEndX = (float) (bulletLine.getX1() + (bulletLine.getX2() - bulletLine.getX1()) * ratio);
            float newEndY = (float) (bulletLine.getY1() + (bulletLine.getY2() - bulletLine.getY1()) * ratio);
            bulletLine.set(bulletLine.getX1(), bulletLine.getY1(), newEndX, newEndY);
        }

        //Bullet Trace Animation erzeugen und der Liste an Bullet Trace Animationen hinzufügen
        BulletTraceAnimation bulletTraceAnimation = new BulletTraceAnimation(bulletLine, entityIsEnemy);
        bulletTraces.add(bulletTraceAnimation);
    }
    
    /**
     * Die Spezielle Version der hitScan-Methode für Scharfschützengewehre!
     * Besonderheit: Der Schuss kann durch mehrere Entitäten durchschießen und somit mehr Treffer landen!
     * 
     * @param weapon Waffe, mit der geschossen wurde
     * @param bulletLine Linie der Schusslaufbahn
     * @param shootingEntity Schießende Entität (soll sich selbst keinen Schaden zufügen können)
     */
    public static void hitScanSniperRifle(Weapon weapon, Line bulletLine, LivingEntity shootingEntity) {

        //Alle lebendigen Entitäten in einem HashSet abspeichern
        HashSet<LivingEntity> livingEntities = new HashSet<LivingEntity>();
        livingEntities.add(PlayerController.getPlayer());
        livingEntities.addAll(EnemyController.getEnemies());

        //Bestimmen, ob die schießende Entität ein Gegner ist
        boolean entityIsEnemy = shootingEntity instanceof Enemy;

        //Alle lebendigen Entitäten iterieren, um zu bestimmen, welche evtl. getroffen wurde
        for (LivingEntity livingEntity : livingEntities) {

            //(schießende Entität hierzu ausschließen, weil Selbstmord ist uncool)
            if (livingEntity.equals(shootingEntity)) {
                continue;
            }

            //Überprüfung auf Überschneidung
            if (livingEntity.getShape().intersects(bulletLine)) {

                //Überprüfung auf Friendly Fire (falls ja, soll kein Schaden gegeben werden)
                if (entityIsEnemy && livingEntity instanceof Enemy) {
                    continue;
                }

                //Schaden zufügen
                livingEntity.takeDamage(weapon.getDamagePerBullet());
            }
        }

        //Bullet Trace Animation erzeugen und der Liste an Bullet Trace Animationen hinzufügen
        BulletTraceAnimation bulletTraceAnimation = new BulletTraceAnimation(bulletLine, entityIsEnemy);
        bulletTraces.add(bulletTraceAnimation);
    }
    

}
