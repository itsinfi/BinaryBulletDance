package Weapons;

/**
 * Diese abstrakte Klasse gibt vor, wie Waffen im Game aufgebaut sein sollen.
 * 
 * @author Sascha Angermann
 */
public class Weapon {
    //TODO: später als abstrakte Oberklasse implementieren

    //Attribute

    protected boolean isSecondary;
    protected short damagePerBullet;
    protected String ammoType;
    protected short firerate;
    protected double spread;
    protected short range;
    // pierce
    protected short magazineSize;
    protected short bullets;
    protected short reloadRate;

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
     */
    public Weapon(boolean isSecondary, short damagePerBullet, String ammoType, short firerate, double spread,
            short range, short magazineSize, short bullets, short reloadRate) {
        this.isSecondary = isSecondary;
        this.damagePerBullet = damagePerBullet;
        this.ammoType = ammoType;
        this.firerate = firerate;
        this.spread = spread;
        this.range = range;
        this.magazineSize = magazineSize;
        this.bullets = bullets;
        this.reloadRate = reloadRate;
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
        if (this.bullets > 0) {
            this.setBullets((short) (this.bullets - 1));
        }
    }

}
