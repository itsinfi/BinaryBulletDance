package Controllers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import Entities.LivingEntity;
import Entities.Player;
import Entities.Weapons.AssaultRifle;
import Entities.Weapons.MachinePistol;
import Entities.Weapons.Pistol;
import Entities.Weapons.Shotgun;
import Entities.Weapons.SniperRifle;
import Entities.Weapon;
import Entities.Animations.BulletFireAnimation;

import java.util.HashMap;

/**
 * Diese Klasse verwaltet alle Spieler im Game.
 * 
 * @author Jeremy Adam
 */
public class PlayerController {

    
    //Attribute

    private static Player player;


    //Konstruktoren

    /**
     * Diese Methode erstellt den PlayerController und den Spieler.
     * 
     * @param container Game Container des Games
     */
    //TODO: Spieler aus GameStateManager entfernen
    public static void init(GameContainer container) throws SlickException {

        //Spieler erzeugen
        player = new Player("assets/playertest_fixed.png", container);

        //Primärwaffe des Spielers erzeugen
        Weapon primary = (Weapon) new Shotgun((LivingEntity) player);
        player.setPrimaryWeapon(primary);//TODO: mit addWeapon(Weapon) methode umsetzen!!! um primär und sekundär zu forcen und auto equippen
        player.setEquippedWeapon(true);//TODO: mit addWeapon(Weapon) methode umsetzen!!! um primär und sekundär zu forcen und auto equippen
        
        //Sekundärwaffe des Spielers erzeugen
        Weapon secondary = (Weapon) new MachinePistol((LivingEntity) player);
        player.setSecondaryWeapon(secondary);//TODO: mit addWeapon(Weapon) methode umsetzen!!! um primär und sekundär zu forcen und auto equippen

        //Spieler Startmunition geben
        HashMap<String, Short> ammo = new HashMap<String, Short>();
        ammo.put(primary.getAmmoType(), (short) 200);
        ammo.put(secondary.getAmmoType(), (short) 200);
        player.setAmmo(ammo);//TODO: stattdessen addAmmo()-Methode, um nicht zu überschreiben
    }


    //Getter

    /**
     * Diese Methode gibt den Spieler als Wert zurück.
     * 
     * @return Spieler des Games
     */
    public static Player getPlayer() {
        return player;
    }


    //Methoden

    /**
     * Diese Methode aktualisiert den Spieler und verwaltet die Inputs der Maus und Tastatur im Spiel.
     * 
     * @param input Mouse- und Keyboard-Input
     * @param delta  Millisekunden seit dem letzten Frame
     * @param container GameContainer des Games
     */
    public static void update(Input input, int delta, GameContainer container) {

        //changeEquippedWeaponTimer aktualisieren
        short changeEquippedWeaponTimer = player.getChangeEquippedWeaponTimer();
        if (changeEquippedWeaponTimer != 0) {
            player.setChangeEquippedWeaponTimer(--changeEquippedWeaponTimer);
        }

        // Spielerbewegung (+ Waffen des Spielers) TODO: iwann schöner handlen mit bulletFire und weapon movement updates
        float playerSpeed = player.getMovementSpeed();
        Weapon primaryWeapon = player.getPrimaryWeapon();
        Weapon secondaryWeapon = player.getSecondaryWeapon();
        BulletFireAnimation primaryBulletFire = primaryWeapon.getBulletFire();
        BulletFireAnimation secondaryBulletFire = secondaryWeapon.getBulletFire();

        if (input.isKeyDown(Input.KEY_W) && player.getShape().getY() > 0) {
            player.setY(player.getShape().getY() - playerSpeed * delta);
            primaryWeapon.setY(primaryWeapon.getShape().getY() - playerSpeed * delta);
            secondaryWeapon.setY(secondaryWeapon.getShape().getY() - playerSpeed * delta);
            primaryBulletFire.setY(primaryBulletFire.getShape().getY() - playerSpeed * delta);
            secondaryBulletFire.setY(secondaryBulletFire.getShape().getY() - playerSpeed * delta);
        }
        if (input.isKeyDown(Input.KEY_S)
                && player.getShape().getY() < container.getHeight() - player.getShape().getHeight()) {
            player.setY(player.getShape().getY() + playerSpeed * delta);
            primaryWeapon.setY(primaryWeapon.getShape().getY() + playerSpeed * delta);
            secondaryWeapon.setY(secondaryWeapon.getShape().getY() + playerSpeed * delta);
            primaryBulletFire.setY(primaryBulletFire.getShape().getY() + playerSpeed * delta);
            secondaryBulletFire.setY(secondaryBulletFire.getShape().getY() + playerSpeed * delta);
        }
        if (input.isKeyDown(Input.KEY_A) && player.getShape().getX() > 0) {
            player.setX(player.getShape().getX() - playerSpeed * delta);
            primaryWeapon.setX(primaryWeapon.getShape().getX() - playerSpeed * delta);
            secondaryWeapon.setX(secondaryWeapon.getShape().getX() - playerSpeed * delta);
            primaryBulletFire.setX(primaryBulletFire.getShape().getX() - playerSpeed * delta);
            secondaryBulletFire.setX(secondaryBulletFire.getShape().getX() - playerSpeed * delta);
        }
        if (input.isKeyDown(Input.KEY_D)
                && player.getShape().getX() < container.getWidth() - player.getShape().getWidth()) {
            player.setX(player.getShape().getX() + playerSpeed * delta);
            primaryWeapon.setX(primaryWeapon.getShape().getX() + playerSpeed * delta);
            secondaryWeapon.setX(secondaryWeapon.getShape().getX() + playerSpeed * delta);
            primaryBulletFire.setX(primaryBulletFire.getShape().getX() + playerSpeed * delta);
            secondaryBulletFire.setX(secondaryBulletFire.getShape().getX() + playerSpeed * delta);
        }
        
        // TODO: DEVELOPER TOOL, REMOVE LATER
        if (input.isKeyDown(Input.KEY_H)) {
        	player.takeDamage((short) 1);
        }
        
        // Spielerausrichtung
        float mouseX = input.getMouseX();
        float mouseY = input.getMouseY();
        float playerX = player.getShape().getX();
        float playerY = player.getShape().getY();

        //Vektor vom Spieler zum Mauszeiger berechnen
        Vector2f playerDirection = new Vector2f(mouseX - playerX, mouseY - playerY);

        //Prüfen, ob die Distanz den Mindestwert überschreitet (um zu verhindern, dass der Spieler z.B. sich selbst treffen kann)
        if (playerDirection.length() > 50) {

            //Richtungsvektor normalisieren (Länge auf 1 setzen)
            playerDirection = playerDirection.normalise();

            //Wert in Grad für den Vektor berechnen (um die Sprites zu rotieren)
            float playerRotationAngle = (float) Math
                    .toDegrees(Math.atan2(playerDirection.getY(), playerDirection.getX()));
            
            //Rotation des Spielers und seiner getragenen Waffen
            player.setDirection(playerRotationAngle);
            primaryWeapon.setDirection(playerRotationAngle);
            secondaryWeapon.setDirection(playerRotationAngle);
        }

        // Ausgerüstete Waffe lesen
        Weapon equippedWeapon = player.getEquippedWeapon();

        // Weapon Slot wechseln
        if (changeEquippedWeaponTimer == 0) {
            if (input.isKeyDown(Input.KEY_1) && equippedWeapon != player.getPrimaryWeapon()) {
                equippedWeapon.setReloadTimer((short) 0);
                player.setEquippedWeapon(true);
            }

            if (input.isKeyDown(Input.KEY_2) && equippedWeapon != player.getSecondaryWeapon()) {
                equippedWeapon.setReloadTimer((short) 0);
                player.setEquippedWeapon(false);
            }
        }

        //Prüfen, ob die Waffe nachgeladen wird oder bereits den nächsten Schuss abgeben darf oder aktuell noch nicht fertig ausgerüstet ist.
        if (equippedWeapon.getReloadTimer() == 0 && equippedWeapon.getFireTimer() == 0
                && player.getChangeEquippedWeaponTimer() == 0) {

            //Prüfen, ob die Waffe automatisch ist und ob die linke Maustaste gedrückt (bzw. gehalten bei automatisch) wurde
            if ((equippedWeapon.getAutomaticFire() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
                    || (!equippedWeapon.getAutomaticFire() && input.isMousePressed(Input.MOUSE_LEFT_BUTTON))) {

                //Waffe schießen
                if (player.getEquippedWeapon().getBullets() > 0) {
                    WeaponController.shoot(player);
                }
            }
        }

        // Prüfen, ob:
        //- 'r' gedrückt wird
        //- Der Spieler den Munitionstyp der ausgerüsteten Waffe im Inventar besitzt
        //- Der Spieler mehr als 0 Schuss für die erforderte Munition im Inventar besitzt
        //- Die Waffe nicht schon voll ist
        if (input.isKeyDown(Input.KEY_R) && equippedWeapon.getReloadTimer() == 0
                && player.getAmmo().containsKey(equippedWeapon.getAmmoType())
                && player.getAmmo().get(equippedWeapon.getAmmoType()) > 0
                && equippedWeapon.getBullets() < equippedWeapon.getMagazineSize()) {
            //ReloadTimer starten
            equippedWeapon.setReloadTimer(equippedWeapon.getReloadRate());

            //Waffe nachladen, sobald der Reload Timer abläuft
        } else if (equippedWeapon.getReloadTimer() == 1) {
            player.reload();
        }
    }
    
    /**
     * Diese Methode aktualisiert die Darstellung des Spielers.
     * 
     * @param g Grafische Darstellung des Spiels durch die Slick2D-Library
     */
    //TODO: Diese Methode in der Main verwenden
    public static void render(Graphics g) {
        player.render(g);
    }
}
