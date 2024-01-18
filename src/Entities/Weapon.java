package Entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

/**
 * Diese abstrakte Klasse gibt vor, wie Waffen im Game aufgebaut sein sollen.
 * 
 * @author Sascha Angermann
 */
public abstract class Weapon extends Entity {
    //TODO: später als abstrakte Oberklasse implementieren

    //Attribute

    protected boolean isSecondary;
    protected short damagePerBullet;
    protected String ammoType;
    protected short firerate;
    protected short fireTimer = 0;
    protected double spread;
    protected short range;
    protected short magazineSize;
    protected short bullets;
    protected short reloadRate;
    protected short reloadTimer = 0;
    protected boolean hasAutomaticFire;
    protected boolean hasInfiniteAmmo = false;

    //Konstruktoren

    /**
     * Diese Methode erzeugt und initialisiert eine Waffe (ausgeführt aus einer Unterklasse).
     * 
     * @param isSecondary True, falls die Waffe eine Sekundärwaffe sein soll, false, falls sie eine Primärwaffe sein soll
     * @param damagePerBullet Schaden pro Schuss (selbe Einheit wie HP)
     * @param ammoType Bezeichner für den Munitionstyp zur Unterscheidung verschiedener Typen
     * @param firerate Zeit in Frames, bis ein nächster Schuss erlaubt ist
     * @param spread Faktor um den Genauigkeit der Waffe festzulegen
     * @param range Reichweite eines Schusses der Waffe
     * @param magazineSize Größe eines vollen Magazins
     * @param bullets Anzahl der aktuell innerhalb der Waffe geladenen Menge an Munition
     * @param reloadRate Zeit in Frames, bis wann nicht erneut nachgeladen werden kann.
     * @param hasAutomaticFire True = Die Waffe verwendet automatisches Feuer, False = Die Waffe verwendet kein automatisches Feuer 
     */
    public Weapon(String spriteAsset, float centerX, float centerY, float direction, boolean isSecondary, short damagePerBullet, String ammoType, short firerate, double spread,
            short range, short magazineSize, short bullets, short reloadRate, boolean hasAutomaticFire) throws SlickException {
        // super(spriteAsset, x, y, width, height, direction);
        super(spriteAsset, centerX, centerY, direction);
        this.isSecondary = isSecondary;
        this.damagePerBullet = damagePerBullet;
        this.ammoType = ammoType;
        this.firerate = firerate;
        this.spread = spread;
        this.range = range;
        this.magazineSize = magazineSize;
        this.bullets = bullets;
        this.reloadRate = reloadRate;
        this.hasAutomaticFire = hasAutomaticFire;
    }

    //Getter

    /**
     * Diese Methode gibt zurück, ob eine Waffe eine Sekundärwaffe ist, oder nicht.
     * 
     * @return True, falls die Waffe eine Sekundärwaffe sein soll, false, falls sie eine Primärwaffe sein soll
     */
    public boolean isSecondary() {
        return isSecondary;
    }

    /**
     * Diese Methode gibt zurück, wie viel Schaden eine Waffe pro Schuss verursacht.
     * 
     * @return Schaden pro Schuss (selbe Einheit wie HP)
     */
    public short getDamagePerBullet() {
        return damagePerBullet;
    }

    /**
     * Diese Methode gibt zurück, was der Munitionstyp der Waffe ist.
     * 
     * @return Bezeichner für den Munitionstyp zur Unterscheidung verschiedener Typen
     */
    public String getAmmoType() {
        return ammoType;
    }

    /**
     * Diese Methode gibt zurück, wie lange ein nächster Schuss nicht möglich ist.
     * 
     * @return Zeit in Frames, bis ein nächster Schuss erlaubt ist
     */
    public short getFirerate() {
        return firerate;
    }

    /**
     * Diese Methode gibt zurück, wie präzise eine Waffe ist.
     * 
     * @return Faktor um den Genauigkeit der Waffe festzulegen
     */
    public double getSpread() {
        return spread;
    }

    /**
     * Diese Methode gibt zurück, wie hoch die Reichweite der Waffe ist.
     * 
     * @return Reichweite eines Schusses der Waffe
     */
    public short getRange() {
        return range;
    }

    /**
     * Diese Methode gibt zurück, wie viel Munition in ein Magazin dieser Waffe passt.
     * 
     * @return Größe eines vollen Magazins
     */
    public short getMagazineSize() {
        return magazineSize;
    }

    /**
     * Diese Methode gibt zurück, was die Anzahl der aktuell geladenen Schuss ist.
     * 
     * @return Anzahl der aktuell innerhalb der Waffe geladenen Menge an Munition
     */
    public short getBullets() {
        return bullets;
    }

    /**
     * Diese Methode gibt zurück, wann eine Waffe erneut nachgeladen oder geschossen werden kann
     * 
     * @return Zeit in Frames, bis wann nicht erneut nachgeladen werden kann.
     */
    public short getReloadRate() {
        return reloadRate;
    }

    /**
     * Diese Methode gibt zurück, ob die Waffe automatisches Feuer bestitzt oder nicht
     * 
     * @return True = Die Waffe verwendet automatisches Feuer, False = Die Waffe verwendet kein automatisches Feuer 
     */
    public boolean getAutomaticFire() {
        return hasAutomaticFire;
    }

    /**
     * Diese Methode gibt zurück, wie viele Frames verbleiben, bis die Waffe erneut nachgeladen werden kann.
     * 
     * @return Zeit in Frames bis die Waffe erneut nachgeladen werden kann
     */
    public short getReloadTimer() {
        return reloadTimer;
    }

    /**
     * Diese Methode gibt zurück, wie viele Frames verbleiben, bis die Waffe erneut geschossen werden kann.
     * 
     * @return Zeit in Frames bis die Waffe erneut geschossen werden kann
     */
    public short getFireTimer() {
        return fireTimer;
    }

    /**
     * Diese Methode gibt zurück, ob eine Waffe unendlich Munition besitzt oder nicht
     * 
     * @param hasInfiniteAmmo True, falls eine Waffe unendlich Munition haben soll, False, falls nicht
     */
    public boolean getInfiniteAmmo() {
        return hasInfiniteAmmo;
    }


    //Setter

    /**
     * Diese Methode legt fest, ob eine Waffe eine Primär- (false) oder Sekundärwaffe (true) ist.
     * 
     * @param isSecondary True, falls die Waffe eine Sekundärwaffe sein soll, false, falls sie eine Primärwaffe sein soll
     */
    public void setSecondary(boolean isSecondary) {
        this.isSecondary = isSecondary;
    }

    /**
     * Diese Methode legt den Schaden pro Schuss der Waffe fest.
     * 
     * @param damagePerBullet Schaden pro Schuss (selbe Einheit wie HP)
     */
    public void setDamagePerBullet(short damagePerBullet) {
        this.damagePerBullet = damagePerBullet;
    }

    /**
     * Diese Methode legt den Bezeichner für den Munitionstyp der Waffe fest.
     * 
     * @param ammoType Bezeichner für den Munitionstyp zur Unterscheidung verschiedener Typen
     */
    public void setAmmoType(String ammoType) {
        this.ammoType = ammoType;
    }

    /**
     * Diese Methode legt die Feuerrate der Waffe fest.
     * 
     * @param firerate Zeit in Frames, bis ein nächster Schuss erlaubt ist
     */
    public void setFirerate(short firerate) {
        this.firerate = firerate;
    }

    /**
     * Diese Methode legt die Präzision der Waffe fest.
     * 
     * @param spread Faktor um den Genauigkeit der Waffe festzulegen
     */
    public void setSpread(double spread) {
        this.spread = spread;
    }

    /**
     * Diese Methode legt die Reichweite der Waffe fest.
     * 
     * @param range Reichweite eines Schusses der Waffe
     */
    public void setRange(short range) {
        this.range = range;
    }

    /**
     * Diese Methode legt die Größe eines vollen Magazins der Waffe fest.
     *  
     * @param magazineSize Größe eines vollen Magazins
     */
    public void setMagazineSize(short magazineSize) {
        this.magazineSize = magazineSize;
    }

    /**
     * Diese Methode legt die aktuelle Anzahl an geladenen Schuss fest.
     * 
     * @param bullets Anzahl der aktuell innerhalb der Waffe geladenen Menge an Munition
     */
    public void setBullets(short bullets) {
        this.bullets = bullets;
    }

    /**
     * Diese Methode legt die Nachladerate der Waffe fest.
     * 
     * @param reloadRate Zeit in Frames, bis wann nicht erneut nachgeladen werden kann.
     */
    public void setReloadRate(short reloadRate) {
        this.reloadRate = reloadRate;
    }

    /**
     * Diese Methode legt fest, ob die Waffe automatisch schießt oder nicht.
     * 
     * @param hasAutomaticFire True = Die Waffe verwendet automatisches Feuer, False = Die Waffe verwendet kein automatisches Feuer 
     */
    public void setAutomaticFire(boolean hasAutomaticFire) {
        this.hasAutomaticFire = hasAutomaticFire;
    }

    /**
     * Diese Methode legt fest, wie viele Frames gewartet werden soll, bis die Waffe erneut nachgeladen werden kann
     * 
     * @param reloadTimer Zeit in Frames bis die Waffe erneut nachgeladen werden kann
     */
    public void setReloadTimer(short reloadTimer) {
        this.reloadTimer = reloadTimer;
    }

    /**
     * Diese Methode legt fest, wie viele Frames gewartet werden soll, bis die Waffe erneut geschossen werden kann
     * 
     * @param fireTimer Zeit in Frames bis die Waffe erneut geschossen werden kann
     */
    public void setFireTimer(short fireTimer) {
        this.fireTimer = fireTimer;
    }

    /**
     * Diese Methode legt fest, ob eine Waffe unendlich Munition besitzt oder nicht
     * 
     * @param hasInfiniteAmmo True, falls eine Waffe unendlich Munition haben soll, False, falls nicht
     */
    public void setInfiniteAmmo(boolean hasInfiniteAmmo) {
        this.hasInfiniteAmmo = hasInfiniteAmmo;
    }


    //Methoden

    /**
     * Diese Methode lädt die Waffe nach.
     * 
     * @param amount Menge der nachgeladenen Munition
     */
    public void reload(short amount) {

        //Prüfen, ob ein negativer Wert vorhanden ist.
        if (amount < 0) {
            System.out.println("Negativer Wert beim Nachladen.");
            return;
        }

        //Neuen geladenen Munitionswert berechnen
        short newBullets = (short) (this.bullets + amount);

        //Prüfen, ob die Menge an neuer Munition die Magazingröße überschreitet
        if (newBullets > this.magazineSize) {
            System.out.println("Es wurde mehr Munition nachgeladen, als in das Magazin passt.");
            newBullets = this.magazineSize;
        }

        //Waffe nachladen
        this.bullets = newBullets;
    }

    //TODO:
    /**
     * Diese Methode zieht den Abzug der Waffe.
     */
    public void attack(/*float xPos, float yPos, float direction*/) {
        //Prüfen, ob das Magazin leer ist, die Waffe nachgeladen wird, oder die Zeit zum letzten Schuss zu kurz für die Waffe ist
        if (this.bullets <= 0 || fireTimer != 0 || reloadTimer != 0) {
            System.out.println("Magazin ist leer.");
        }

        //Munition updaten
        this.setBullets((short) (this.bullets - 1));
    }

    /**
     * Diese Methode stellt die Waffe visuell im Level dar
     * 
     * @param rotationAngle Rotation des Trägers der Waffe
     */
    public void render(float rotationAngle) {
        this.sprite.setRotation(rotationAngle);
        this.sprite.draw(this.shape.getX(), this.shape.getY());
    }
}
