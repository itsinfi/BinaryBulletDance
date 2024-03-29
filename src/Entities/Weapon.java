package Entities;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import Entities.Animations.BulletFireAnimation;

/**
 * Diese abstrakte Klasse gibt vor, wie Waffen im Game aufgebaut sein sollen.
 * 
 * @author Sascha Angermann
 */
public abstract class Weapon extends Entity {

    //Attribute

    protected short damagePerBullet;
    protected String ammoType;
    protected short firerate;
    protected short fireTimer = 0;
    protected float accuracy;
    protected short range;
    protected short magazineSize;
    protected short bullets;
    protected short reloadRate;
    protected short reloadTimer = 0;
    protected boolean hasAutomaticFire;
    protected boolean hasInfiniteAmmo = false;
    protected float offsetX;
    protected float offsetY;
    protected BulletFireAnimation bulletFire;
    protected float bulletFireOffsetX;
    protected float bulletFireOffsetY;
    protected Sound shootSound;
    protected Sound reloadSound;

    //Konstruktoren

    /**
     * Diese Methode erzeugt und initialisiert eine Waffe (ausgeführt aus einer Unterklasse).
     * 
     * @param spriteAsset Pfad zum Image-Asset zur Darstellung der Entität
     * @param centerX Zentrale x-Koordinate der Position der Entität
     * @param centerY Zentrale y-Koordinate der Position der Entität
     * @param direction Blickrichtung der Entität (in Grad)
     * @param damagePerBullet Schaden pro Schuss (selbe Einheit wie HP)
     * @param ammoType Bezeichner für den Munitionstyp zur Unterscheidung verschiedener Typen
     * @param firerate Zeit in Frames, bis ein nächster Schuss erlaubt ist
     * @param accuracy Präzision der Waffe (Achtung: Wert ist empfindlicher je geringer die Reichweite ist)
     * @param range Reichweite eines Schusses der Waffe
     * @param magazineSize Größe eines vollen Magazins
     * @param reloadRate Zeit in Frames, bis wann nicht erneut nachgeladen werden kann.
     * @param hasAutomaticFire True = Die Waffe verwendet automatisches Feuer, False = Die Waffe verwendet kein automatisches Feuer
     * @param offsetX Anzahl an Pixel, um welche die Waffe horizontal (wenn man nach rechts schaut) versetzt zum Träger der Waffe gerendert werden soll
     * @param offsetY Anzahl an Pixel, um welche die Waffe vertikal (wenn man nach rechts schaut) versetzt zum Träger der Waffe gerendert werden soll
     * @param bulletFireOffsetX Anzahl an Pixel, um welche das Schussfeuer horizontal (wenn man nach rechts schaut) versetzt zum Träger der Waffe gerendert werden soll
     * @param bulletFireOffsetY Anzahl an Pixel, um welche das Schussfeuer vertikal (wenn man nach rechts schaut) versetzt zum Träger der Waffe gerendert werden soll
     * @param shootSound Sound, der gespielt werden soll, wenn die Waffe schießt
     */
    public Weapon(String spriteAsset, float centerX, float centerY, float direction, short damagePerBullet, String ammoType, short firerate, float accuracy,
            short range, short magazineSize, short reloadRate, boolean hasAutomaticFire, float offsetX, float offsetY, float bulletFireOffsetX, float bulletFireOffsetY, Sound shootSound) throws SlickException {
        //Entity erzeugen
        super(spriteAsset, centerX, centerY, direction);

        //Schussfeuer erzeugen (zum Rendern des Schussfeuers)
        this.bulletFire = new BulletFireAnimation(centerX, centerY, direction, bulletFireOffsetX + offsetX, bulletFireOffsetY + offsetY);
        
        //Image auf der y-Achse spiegeln (Charakter soll Rechtshänder sein)
        this.sprite = this.sprite.getFlippedCopy(false, true);

        //Werte einfügen
        this.damagePerBullet = damagePerBullet;
        this.ammoType = ammoType;
        this.firerate = firerate;
        this.accuracy = accuracy;
        this.range = range;
        this.magazineSize = magazineSize;
        this.bullets = magazineSize;
        this.reloadRate = reloadRate;
        this.hasAutomaticFire = hasAutomaticFire;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.shootSound = shootSound;
        this.reloadSound = new Sound("assets/sounds/reload.wav");
    }

    //Getter

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
     * @return Präzision der Waffe (Achtung: Wert ist empfindlicher je geringer die Reichweite ist)
     */
    public float getAccuracy() {
        return accuracy;
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
     * @return hasInfiniteAmmo True, falls eine Waffe unendlich Munition haben soll, False, falls nicht
     */
    public boolean getInfiniteAmmo() {
        return hasInfiniteAmmo;
    }

    /**
     * Diese Methode gibt zurück, wie hoch die Verschiebung der Waffe auf der x-Achse zum Träger der Waffe ist
     * 
     * @return Anzahl an Pixel, um welche die Waffe horizontal (wenn man nach rechts schaut) versetzt zum Träger der Waffe gerendert werden soll
     */
    public float getOffsetX() {
        return offsetX;
    }

    /**
     * Diese Methode gibt zurück, wie hoch die Verschiebung der Waffe auf der y-Achse zum Träger der Waffe ist
     * 
     * @return Anzahl an Pixel, um welche die Waffe vertikal (wenn man nach rechts schaut) versetzt zum Träger der Waffe gerendert werden soll
     */
    public float getOffsetY() {
        return offsetY;
    }

    /**
     * Diese Methode gibt zurück, wie hoch die Verschiebung des Waffenfeuers auf der x-Achse zur Waffe ist
     * 
     * @return Anzahl an Pixel, um welche das Waffenfeuer horizontal (wenn man nach rechts schaut) versetzt zur Waffe gerendert werden soll
     */
    public float getBulletFireOffsetX() {
        return offsetX;
    }

    /**
     * Diese Methode gibt zurück, wie hoch die Verschiebung der Waffenfeuers auf der y-Achse zur Waffe ist
     * 
     * @return Anzahl an Pixel, um welche das Waffenfeuer vertikal (wenn man nach rechts schaut) versetzt zur Waffe gerendert werden soll
     */
    public float getBulletFireOffsetY() {
        return offsetY;
    }

    /**
     * Diese Methode gibt die Animationsklasse für das Schussfeuer zurück
     * 
     * @return Animationsklasse für das Schussfeuer
     */
    public BulletFireAnimation getBulletFire() {
        return bulletFire;
    }


    //Setter

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
     * @param accuracy Präzision der Waffe (Achtung: Wert ist empfindlicher je geringer die Reichweite ist)
     */
    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
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

    /**
     * Diese Methode legt die Blickrichtung der Waffe und dessen Schussfeuers fest
     * 
     * @param direction Blickrichtung der Waffe (in Grad)
     */
    public void setDirection(float direction) {
        this.direction = direction;
        this.bulletFire.setDirection(direction);
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

        //Nachlade-Sound abspielen
        this.playReloadSound();
    }

    /**
     * Diese Methode zieht den Abzug der Waffe.
     */
    public void attack() {
        //Prüfen, ob das Magazin leer ist, die Waffe nachgeladen wird, oder die Zeit zum letzten Schuss zu kurz für die Waffe ist
        if (this.bullets <= 0) {
            System.out.println("Magazin ist leer.");
            return;
        }

        if (this.fireTimer != 0 ) {
            System.out.println("Waffe kann noch nicht geschossen werden.");
            return;
        }

        if (this.reloadTimer != 0) {
            System.out.println("Waffe wird nachgeladen.");
            return;
        }

        //Munition updaten
        this.setBullets((short) (this.bullets - 1));

        //Schussfeuer animieren
        this.bulletFire.animate();

        //Schuss-Sound abspielen
        this.playShootSound();
    }

    /**
     * Diese Methode stellt die Waffe visuell im Level dar
     */
    public void render() {

        //Die Position der Waffe lesen
        float weaponX = this.shape.getX();
        float weaponY = this.shape.getY();

        //Den rotierten Offset berechnen (um die Waffe in der Hand darzustellen)
        float rotatedOffsetX = (float) (this.offsetX * Math.cos(Math.toRadians(direction))
                - this.offsetY * Math.sin(Math.toRadians(direction)));
        float rotatedOffsetY = (float) (this.offsetX * Math.sin(Math.toRadians(direction))
                + this.offsetY * Math.cos(Math.toRadians(direction)));

        //Den rotierten Offset zu den Waffenkoordinaten addieren
        float x = weaponX + rotatedOffsetX;
        float y = weaponY + rotatedOffsetY;

        //Rotation hinzufügen
        this.sprite.setRotation(direction);

        //Sprite rendern
        this.sprite.draw(x, y);
    }

    /**
     * Diese Methode spielt den Schuss-Sound der Waffe ab.
     */
    protected void playShootSound() {
        if (shootSound != null) {
            shootSound.play(1f, 0.3f);
        }
    }

    /**
     * Diese Methode spielt den Nachlade-Sound der Waffe ab.
     */
    protected void playReloadSound() {
        if (reloadSound != null) {
            reloadSound.play();
        }
    }
}
