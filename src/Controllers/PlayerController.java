package Controllers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import Entities.LivingEntity;
import Entities.Player;
import Entities.Weapons.Primary;
import Entities.Weapons.Secondary;
import Entities.Weapon;

import java.util.HashMap;

/**
 * Diese Klasse verwaltet alle Spieler im Game.
 * 
 * @author Jeremy Adam
 */
public class PlayerController {

    
    //Attribute

    private Player player;


    //Konstruktoren

    /**
     * Diese Methode erstellt den PlayerController und den Spieler.
     * 
     * @param container Game Container des Games
     */
    //TODO: mit static init Methode ersetzen
    //TODO: alle Methoden und Attribute static machen
    //TODO: Spieler aus GameStateManager entfernen
    public PlayerController(GameContainer container) throws SlickException {

        //Spieler erzeugen
        player = new Player("assets/playertest_fixed.png", container);

        //Primärwaffe des Spielers erzeugen
        Weapon primary = (Weapon) new Primary((LivingEntity) player);
        player.setPrimaryWeapon(primary);//TODO: mit addWeapon(Weapon) methode umsetzen!!! um primär und sekundär zu forcen und auto equippen
        player.setEquippedWeapon(true);//TODO: mit addWeapon(Weapon) methode umsetzen!!! um primär und sekundär zu forcen und auto equippen
        
        //Sekundärwaffe des Spielers erzeugen
        Weapon secondary = (Weapon) new Secondary((LivingEntity) player);
        player.setSecondaryWeapon(secondary);//TODO: mit addWeapon(Weapon) methode umsetzen!!! um primär und sekundär zu forcen und auto equippen

        //Spieler Startmunition geben
        HashMap<String, Short> ammo = new HashMap<String, Short>();
        ammo.put("PRIMARY", (short) 200);
        ammo.put("SECONDARY", (short) 200);
        player.setAmmo(ammo);//TODO: stattdessen addAmmo()-Methode, um nicht zu überschreiben
    }


    //Getter

    /**
     * Diese Methode gibt den Spieler als Wert zurück.
     * 
     * @return Spieler des Games
     */
    public Player getPlayer() {
        return player;
    }


    //Methodenb

    /**
     * Diese Methode aktualisiert den Spieler und verwaltet die Inputs der Maus und Tastatur im Spiel.
     * 
     * @param input Mouse- und Keyboard-Input
     * @param delta  Millisekunden seit dem letzten Frame
     * @param container GameContainer des Games
     */
    public void update(Input input, int delta, GameContainer container) {

        //changeEquippedWeaponTimer aktualisieren
        short changeEquippedWeaponTimer = player.getChangeEquippedWeaponTimer();
        if (changeEquippedWeaponTimer != 0) {
            player.setChangeEquippedWeaponTimer(--changeEquippedWeaponTimer);
        }

        // Spielerbewegung (+ Waffen des Spielers)
        float playerSpeed = player.getMovementSpeed();
        Weapon primaryWeapon = player.getPrimaryWeapon();
        Weapon secondaryWeapon = player.getSecondaryWeapon();

        if (input.isKeyDown(Input.KEY_W) && player.getShape().getY() > 0) {
            player.setY(player.getShape().getY() - playerSpeed * delta);
            primaryWeapon.setY(primaryWeapon.getShape().getY() - playerSpeed * delta);
            secondaryWeapon.setY(secondaryWeapon.getShape().getY() - playerSpeed * delta);
        }
        if (input.isKeyDown(Input.KEY_S)
                && player.getShape().getY() < container.getHeight() - player.getShape().getHeight()) {
            player.setY(player.getShape().getY() + playerSpeed * delta);
            primaryWeapon.setY(primaryWeapon.getShape().getY() + playerSpeed * delta);
            secondaryWeapon.setY(secondaryWeapon.getShape().getY() + playerSpeed * delta);
        }
        if (input.isKeyDown(Input.KEY_A) && player.getShape().getX() > 0) {
            player.setX(player.getShape().getX() - playerSpeed * delta);
            primaryWeapon.setX(primaryWeapon.getShape().getX() - playerSpeed * delta);
            secondaryWeapon.setX(secondaryWeapon.getShape().getX() - playerSpeed * delta);
        }
        if (input.isKeyDown(Input.KEY_D)
                && player.getShape().getX() < container.getWidth() - player.getShape().getWidth()) {
            player.setX(player.getShape().getX() + playerSpeed * delta);
            primaryWeapon.setX(primaryWeapon.getShape().getX() + playerSpeed * delta);
            secondaryWeapon.setX(secondaryWeapon.getShape().getX() + playerSpeed * delta);
        }
        
        // Spielerausrichtung
        float mouseX = input.getMouseX();
        float mouseY = input.getMouseY();
        float playerX = player.getShape().getX();
        float playerY = player.getShape().getY();
        
        Vector2f playerDirection = new Vector2f(mouseX - playerX, mouseY - playerY).normalise();
        
        float playerRotationAngle = (float) Math.toDegrees(Math.atan2(playerDirection.getY(), playerDirection.getX()));
        player.setDirection(playerRotationAngle);

        
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
        if (equippedWeapon.getReloadTimer() == 0 && equippedWeapon.getFireTimer() == 0 && player.getChangeEquippedWeaponTimer() == 0) {

            //Prüfen, ob die Waffe automatisch ist und ob die linke Maustaste gedrückt (bzw. gehalten bei automatisch) wurde
            if ((equippedWeapon.getAutomaticFire() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
                    || (!equippedWeapon.getAutomaticFire() && input.isMousePressed(Input.MOUSE_LEFT_BUTTON))) {
                
                //Waffe schießen
                if (player.getEquippedWeapon().getBullets() > 0) {
                    WeaponController.shoot(input, this.player);
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
    public void render(Graphics g) {
        this.player.render(g);
    }
}
