package Controllers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import Entities.Player;
import Entities.Weapons.AssaultRifle;
import Entities.Weapons.MachinePistol;
import Entities.Weapons.Pistol;
import Entities.Weapons.Shotgun;
import Entities.Weapons.SniperRifle;
import Entities.Weapon;
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
     * @param container container of the window
     */
    //TODO: Spieler aus GameStateManager entfernen
    public static void init(GameContainer container) throws SlickException {

        //Spieler erzeugen
        player = new Player("assets/playertest_fixed.png", container);

        //Waffenslot 1
        Weapon weapon1 = (Weapon) new AssaultRifle(player);
        player.addWeapon(weapon1, (short) 1);
        player.setEquippedWeapon((short) 1);

        //Waffenslot 2
        Weapon weapon2 = (Weapon) new MachinePistol(player);
        player.addWeapon(weapon2, (short) 2);

        //Waffenslot 3
        Weapon weapon3 = (Weapon) new Shotgun(player);
        player.addWeapon(weapon3, (short) 3);

        //Waffenslot 4
        Weapon weapon4 = (Weapon) new SniperRifle(player);
        player.addWeapon(weapon4, (short) 4);
        
        //Waffenslot 5
        Weapon weapon5 = (Weapon) new Pistol(player);
        player.addWeapon(weapon5, (short) 5);

        //Spieler Startmunition geben
        HashMap<String, Short> ammo = new HashMap<String, Short>();
        ammo.put("ASSAULT_RIFLE", (short) 200);
        ammo.put("MACHINE_PISTOL", (short) 200);
        ammo.put("SHOTGUN", (short) 100);
        ammo.put("SNIPER_RIFLE", (short) 50);
        ammo.put("PISTOL", (short) 1);
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
     * @param container container of the window
     */
    public static void update(Input input, int delta, GameContainer container) {

        //changeEquippedWeaponTimer aktualisieren
        short changeEquippedWeaponTimer = player.getChangeEquippedWeaponTimer();
        if (changeEquippedWeaponTimer != 0) {
            player.setChangeEquippedWeaponTimer(--changeEquippedWeaponTimer);
        }

        // Spielerbewegung (+ Waffen des Spielers) TODO: iwann schöner handlen mit bulletFire und weapon movement updates
        float playerSpeed = player.getMovementSpeed();
        Weapon weapon1 = player.getWeaponSlot((short) 1);
        Weapon weapon2 = player.getWeaponSlot((short) 2);
        Weapon weapon3 = player.getWeaponSlot((short) 3);
        Weapon weapon4 = player.getWeaponSlot((short) 4);
        Weapon weapon5 = player.getWeaponSlot((short) 5);
        DamageAnimation damageAnimation = player.getDamageAnimation();

        // Ausgerüstete Waffe lesen
        Weapon equippedWeapon = player.getEquippedWeapon();

        //Damage-Animation updaten
        damageAnimation.update();

        //Keyboard-Input auslesen und Spielerkollisionen checken

           if (input.isKeyDown(Input.KEY_W) && player.getShape().getCenterY() - player.getShape().getHeight()/2 > 0) {
        	
        	float currentX = player.getShape().getCenterX();
            float currentY = player.getShape().getCenterY();
            
            direction = Direction.UP;
            
            if (!(LevelController.getIsHittingCollision(currentX, currentY - playerSpeed * delta))) {
            		movePlayer(delta, playerSpeed, weapon1, weapon2, weapon3, weapon4, weapon5, damageAnimation);
            	}
            }
            
        if (input.isKeyDown(Input.KEY_S) && player.getShape().getY() < (container.getHeight() + LevelController.getCameraY()) - player.getShape().getHeight()) {
        	
        	float currentX = player.getShape().getCenterX();
        	float currentY = player.getShape().getCenterY();
            
            direction = Direction.DOWN;
            
            if (!(LevelController.getIsHittingCollision(currentX, currentY + playerSpeed * delta))) {
            	
            	movePlayer(delta, playerSpeed, weapon1, weapon2, weapon3, weapon4, weapon5, damageAnimation);
        	}

           
        }
        

        if (input.isKeyDown(Input.KEY_A) && player.getShape().getCenterX() - player.getShape().getWidth()/2 > 0) {
        	float currentX = player.getShape().getCenterX();
        	float currentY = player.getShape().getCenterY();
            
            direction = Direction.LEFT;
            
            if (!(LevelController.getIsHittingCollision(currentX - playerSpeed * delta, currentY))) {
            	movePlayer(delta, playerSpeed, weapon1, weapon2, weapon3, weapon4, weapon5, damageAnimation);
        	}
        }

        if (input.isKeyDown(Input.KEY_D) && player.getShape().getX() < (container.getWidth() + LevelController.getCameraX()) - player.getShape().getWidth()) {
        	
        	float currentX = player.getShape().getCenterX();
        	float currentY = player.getShape().getCenterY();
            
            direction = Direction.RIGHT;
            
            if (!(LevelController.getIsHittingCollision(currentX + playerSpeed * delta, currentY))) {
            	movePlayer(delta, playerSpeed, weapon1, weapon2, weapon3, weapon4, weapon5, damageAnimation);
        	}
        	
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
        if (playerDirection.length() > equippedWeapon.getOffsetX() + equippedWeapon.getShape().getWidth() + 30) {
            playerIsTooNear = false;

            //Richtungsvektor normalisieren (Länge auf 1 setzen)
            playerDirection = playerDirection.normalise();

            //Wert in Grad für den Vektor berechnen (um die Sprites zu rotieren)
            float playerRotationAngle = (float) Math
                    .toDegrees(Math.atan2(playerDirection.getY(), playerDirection.getX()));

            //Rotation des Spielers und seiner getragenen Waffen
            player.setDirection(playerRotationAngle);
            weapon1.setDirection(playerRotationAngle);
            weapon2.setDirection(playerRotationAngle);
            weapon3.setDirection(playerRotationAngle);
            weapon4.setDirection(playerRotationAngle);
            weapon5.setDirection(playerRotationAngle);
            damageAnimation.setDirection(playerRotationAngle);
        }

        // Weapon Slot wechseln
        if (changeEquippedWeaponTimer == 0) {
            if (input.isKeyDown(Input.KEY_1) && equippedWeapon != weapon1) {
                equippedWeapon.setReloadTimer((short) 0);
                player.setEquippedWeapon((short) 1);
            }

            if (input.isKeyDown(Input.KEY_2) && equippedWeapon != weapon2) {
                equippedWeapon.setReloadTimer((short) 0);
                player.setEquippedWeapon((short) 2);
            }

            if (input.isKeyDown(Input.KEY_3) && equippedWeapon != weapon3) {
                equippedWeapon.setReloadTimer((short) 0);
                player.setEquippedWeapon((short) 3);
            }

            if (input.isKeyDown(Input.KEY_4) && equippedWeapon != weapon4) {
                equippedWeapon.setReloadTimer((short) 0);
                player.setEquippedWeapon((short) 4);
            }

            if (input.isKeyDown(Input.KEY_5) && equippedWeapon != weapon5) {
                equippedWeapon.setReloadTimer((short) 0);
                player.setEquippedWeapon((short) 5);
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
    
    public static void movePlayer(int delta, float playerSpeed, Weapon weapon1, Weapon weapon2, Weapon weapon3, Weapon weapon4, Weapon weapon5, DamageAnimation damageAnimation) {
        
        //Nach oben laufen
        if (direction == Direction.UP) {
            player.setY(player.getShape().getY() - playerSpeed * delta);
            weapon1.setY(weapon1.getShape().getY() - playerSpeed * delta);
            weapon2.setY(weapon2.getShape().getY() - playerSpeed * delta);
            weapon3.setY(weapon3.getShape().getY() - playerSpeed * delta);
            weapon4.setY(weapon4.getShape().getY() - playerSpeed * delta);
            weapon5.setY(weapon5.getShape().getY() - playerSpeed * delta);
            weapon1.getBulletFire().setY(weapon1.getBulletFire().getShape().getY() - playerSpeed * delta);
            weapon2.getBulletFire().setY(weapon2.getBulletFire().getShape().getY() - playerSpeed * delta);
            weapon3.getBulletFire().setY(weapon3.getBulletFire().getShape().getY() - playerSpeed * delta);
            weapon4.getBulletFire().setY(weapon4.getBulletFire().getShape().getY() - playerSpeed * delta);
            weapon5.getBulletFire().setY(weapon5.getBulletFire().getShape().getY() - playerSpeed * delta);
            player.getDamageAnimation().setY(damageAnimation.getShape().getY() - playerSpeed * delta);

        //Nach links laufen
        } else if (direction == Direction.LEFT) {
            player.setX(player.getShape().getX() - playerSpeed * delta);
            weapon1.setX(weapon1.getShape().getX() - playerSpeed * delta);
            weapon2.setX(weapon2.getShape().getX() - playerSpeed * delta);
            weapon3.setX(weapon3.getShape().getX() - playerSpeed * delta);
            weapon4.setX(weapon4.getShape().getX() - playerSpeed * delta);
            weapon5.setX(weapon5.getShape().getX() - playerSpeed * delta);
            weapon1.getBulletFire().setX(weapon1.getBulletFire().getShape().getX() - playerSpeed * delta);
            weapon2.getBulletFire().setX(weapon2.getBulletFire().getShape().getX() - playerSpeed * delta);
            weapon3.getBulletFire().setX(weapon3.getBulletFire().getShape().getX() - playerSpeed * delta);
            weapon4.getBulletFire().setX(weapon4.getBulletFire().getShape().getX() - playerSpeed * delta);
            weapon5.getBulletFire().setX(weapon5.getBulletFire().getShape().getX() - playerSpeed * delta);
            damageAnimation.setX(damageAnimation.getShape().getX() - playerSpeed * delta);

        //Nach unten laufen
        } else if (direction == Direction.DOWN) {
            player.setY(player.getShape().getY() + playerSpeed * delta);
            weapon1.setY(weapon1.getShape().getY() + playerSpeed * delta);
            weapon2.setY(weapon2.getShape().getY() + playerSpeed * delta);
            weapon3.setY(weapon3.getShape().getY() + playerSpeed * delta);
            weapon4.setY(weapon4.getShape().getY() + playerSpeed * delta);
            weapon5.setY(weapon5.getShape().getY() + playerSpeed * delta);
            weapon1.getBulletFire().setY(weapon1.getBulletFire().getShape().getY() + playerSpeed * delta);
            weapon2.getBulletFire().setY(weapon2.getBulletFire().getShape().getY() + playerSpeed * delta);
            weapon3.getBulletFire().setY(weapon3.getBulletFire().getShape().getY() + playerSpeed * delta);
            weapon4.getBulletFire().setY(weapon4.getBulletFire().getShape().getY() + playerSpeed * delta);
            weapon5.getBulletFire().setY(weapon5.getBulletFire().getShape().getY() + playerSpeed * delta);
            damageAnimation.setY(damageAnimation.getShape().getY() + playerSpeed * delta);

        //Nach rechts laufen
        } else if (direction == Direction.RIGHT) {
            player.setX(player.getShape().getX() + playerSpeed * delta);
            weapon1.setX(weapon1.getShape().getX() + playerSpeed * delta);
            weapon2.setX(weapon2.getShape().getX() + playerSpeed * delta);
            weapon3.setX(weapon3.getShape().getX() + playerSpeed * delta);
            weapon4.setX(weapon4.getShape().getX() + playerSpeed * delta);
            weapon5.setX(weapon5.getShape().getX() + playerSpeed * delta);
            weapon1.getBulletFire().setX(weapon1.getBulletFire().getShape().getX() + playerSpeed * delta);
            weapon2.getBulletFire().setX(weapon2.getBulletFire().getShape().getX() + playerSpeed * delta);
            weapon3.getBulletFire().setX(weapon3.getBulletFire().getShape().getX() + playerSpeed * delta);
            weapon4.getBulletFire().setX(weapon4.getBulletFire().getShape().getX() + playerSpeed * delta);
            weapon5.getBulletFire().setX(weapon5.getBulletFire().getShape().getX() + playerSpeed * delta);
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
