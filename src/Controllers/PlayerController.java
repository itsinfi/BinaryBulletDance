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
import Entities.Animations.DamageAnimation;

import java.util.HashMap;

/**
 * Diese Klasse verwaltet alle Spieler im Game.
 * 
 * @author Jeremy Adam
 */
public abstract class PlayerController {

    
    //Attribute

    private static Player player;

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public static Direction direction;


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
        Weapon primary = (Weapon) new AssaultRifle((LivingEntity) player);
        player.addWeapon(primary);
        player.setEquippedWeapon(true);
        
        //Sekundärwaffe des Spielers erzeugen
        Weapon secondary = (Weapon) new Pistol((LivingEntity) player);
        player.addWeapon(secondary);

        //Spieler Startmunition geben
        HashMap<String, Short> ammo = new HashMap<String, Short>();
        ammo.put("ASSAULT_RIFLE", (short) 200);
        ammo.put("PISTOL", (short) 200);
        player.addAmmo(ammo);
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
        DamageAnimation damageAnimation = player.getDamageAnimation();

        //Damage-Animation updaten
        damageAnimation.update();

        //Keyboard-Input auslesen und Spielerkollisionen checken
        if (input.isKeyDown(Input.KEY_W) && player.getShape().getY() > 0) {
            direction = !LevelController.getIsHittingCollision(player.getShape()) ? Direction.UP : Direction.UP;
            movePlayer(delta, playerSpeed, primaryWeapon, secondaryWeapon, primaryBulletFire, secondaryBulletFire, damageAnimation);
        }
        if (input.isKeyDown(Input.KEY_S) && player.getShape().getY() < (container.getHeight() + LevelController.getCameraY()) - player.getShape().getHeight()) {
            direction = !LevelController.getIsHittingCollision(player.getShape()) ? Direction.DOWN : Direction.DOWN;
            movePlayer(delta, playerSpeed, primaryWeapon, secondaryWeapon, primaryBulletFire, secondaryBulletFire, damageAnimation);
        }
        if (input.isKeyDown(Input.KEY_A) && player.getShape().getX() > 0) {
            direction = !LevelController.getIsHittingCollision(player.getShape()) ? Direction.LEFT : Direction.LEFT;
            movePlayer(delta, playerSpeed, primaryWeapon, secondaryWeapon, primaryBulletFire, secondaryBulletFire, damageAnimation);
        }
        if (input.isKeyDown(Input.KEY_D) && player.getShape().getX() < (container.getWidth() + LevelController.getCameraX()) - player.getShape().getWidth()) {
            direction = !LevelController.getIsHittingCollision(player.getShape()) ? Direction.RIGHT : Direction.RIGHT;
            movePlayer(delta, playerSpeed, primaryWeapon, secondaryWeapon, primaryBulletFire, secondaryBulletFire, damageAnimation);
        }
        
        if (input.isKeyDown(Input.KEY_H)) {
        	player.takeDamage((short) 1);
        }

        // Spielerausrichtung
        float mouseX = input.getMouseX() + LevelController.getCameraX();
        float mouseY = input.getMouseY() + LevelController.getCameraY();
        float playerX = player.getShape().getX();
        float playerY = player.getShape().getY();

        //Vektor vom Spieler zum Mauszeiger berechnen
        Vector2f playerDirection = new Vector2f(mouseX - playerX, mouseY - playerY);

        //Prüfen, ob die Distanz den Mindestwert überschreitet (um zu verhindern, dass der Spieler z.B. sich selbst treffen kann)
        boolean playerIsTooNear = true;
        if (playerDirection.length() > 150) {
            playerIsTooNear = false;

            //Richtungsvektor normalisieren (Länge auf 1 setzen)
            playerDirection = playerDirection.normalise();

            //Wert in Grad für den Vektor berechnen (um die Sprites zu rotieren)
            float playerRotationAngle = (float) Math
                    .toDegrees(Math.atan2(playerDirection.getY(), playerDirection.getX()));

            //Rotation des Spielers und seiner getragenen Waffen
            player.setDirection(playerRotationAngle);
            primaryWeapon.setDirection(playerRotationAngle);
            secondaryWeapon.setDirection(playerRotationAngle);
            damageAnimation.setDirection(playerRotationAngle);
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

        //Prüfen, ob die Waffe aktuell noch nicht fertig ausgerüstet ist.
        if (player.getChangeEquippedWeaponTimer() == 0) {

            //Prüfen, ob die Waffe automatisch ist und ob die linke Maustaste gedrückt (bzw. gehalten bei automatisch) wurde
            if ((equippedWeapon.getAutomaticFire() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
                    || (!equippedWeapon.getAutomaticFire() && input.isMousePressed(Input.MOUSE_LEFT_BUTTON))) {

                //Waffe schießen
                WeaponController.shoot(player, playerIsTooNear ? null : mouseX, playerIsTooNear ? null : mouseY);
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
    
    public static void movePlayer(int delta, float playerSpeed, Weapon primaryWeapon, Weapon secondaryWeapon, BulletFireAnimation primaryBulletFire,
            BulletFireAnimation secondaryBulletFire, DamageAnimation damageAnimation) {
        
        //Nach oben laufen
        if (direction == Direction.UP) {
            player.setY(player.getShape().getY() - playerSpeed * delta);
            player.getPrimaryWeapon().setY(primaryWeapon.getShape().getY() - playerSpeed * delta);
            player.getSecondaryWeapon().setY(secondaryWeapon.getShape().getY() - playerSpeed * delta);
            player.getPrimaryWeapon().getBulletFire().setY(primaryBulletFire.getShape().getY() - playerSpeed * delta);
            player.getSecondaryWeapon().getBulletFire().setY(secondaryBulletFire.getShape().getY() - playerSpeed * delta);
            player.getDamageAnimation().setY(damageAnimation.getShape().getY() - playerSpeed * delta);

        //Nach links laufen
        } else if (direction == Direction.LEFT) {
            player.setX(player.getShape().getX() - playerSpeed * delta);
            primaryWeapon.setX(primaryWeapon.getShape().getX() - playerSpeed * delta);
            secondaryWeapon.setX(secondaryWeapon.getShape().getX() - playerSpeed * delta);
            primaryBulletFire.setX(primaryBulletFire.getShape().getX() - playerSpeed * delta);
            secondaryBulletFire.setX(secondaryBulletFire.getShape().getX() - playerSpeed * delta);
            damageAnimation.setX(damageAnimation.getShape().getX() - playerSpeed * delta);

        //Nach unten laufen
        } else if (direction == Direction.DOWN) {
            player.setY(player.getShape().getY() + playerSpeed * delta);
            primaryWeapon.setY(primaryWeapon.getShape().getY() + playerSpeed * delta);
            secondaryWeapon.setY(secondaryWeapon.getShape().getY() + playerSpeed * delta);
            primaryBulletFire.setY(primaryBulletFire.getShape().getY() + playerSpeed * delta);
            secondaryBulletFire.setY(secondaryBulletFire.getShape().getY() + playerSpeed * delta);
            damageAnimation.setY(damageAnimation.getShape().getY() + playerSpeed * delta);

        //Nach rechts laufen
        } else if (direction == Direction.RIGHT) {
            player.setX(player.getShape().getX() + playerSpeed * delta);
            primaryWeapon.setX(primaryWeapon.getShape().getX() + playerSpeed * delta);
            secondaryWeapon.setX(secondaryWeapon.getShape().getX() + playerSpeed * delta);
            primaryBulletFire.setX(primaryBulletFire.getShape().getX() + playerSpeed * delta);
            secondaryBulletFire.setX(secondaryBulletFire.getShape().getX() + playerSpeed * delta);
            damageAnimation.setX(damageAnimation.getShape().getX() + playerSpeed * delta);
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
