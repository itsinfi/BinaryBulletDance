package Controllers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import Entities.Player;
import Weapons.Weapon;

import java.util.HashMap;

/**
 * Diese Klasse verwaltet alle Spieler im Game.
 * 
 * @author Jeremy Adam
 */
public class PlayerController {

    
    //Attribute

    private Player player;
    private int reloadTimer = 0;//TODO: im WeaponController implementieren


    //Konstruktoren

    /**
     * Diese Methode erstellt den PlayerController und den Spieler.
     * 
     */
    //TODO: mit static init Methode ersetzen
    //TODO: alle Methoden und Attribute static machen
    //TODO: Spieler aus GameStateManager entfernen
    public PlayerController(GameContainer container) throws SlickException {
        Weapon primary = new Weapon(false, (short) 30, "PRIMARY", (short) 2000, 0.0, (short) 200, (short) 30,
                (short) 30, (short) 120);
        Weapon secondary = new Weapon(true, (short) 30, "SECONDARY", (short) 2000, 0.0, (short) 200, (short) 15,
                (short) 15, (short) 1000);

        player = new Player("assets/playertest.png", container, primary, primary, secondary);

        HashMap<String, Short> ammo = new HashMap<String, Short>();
        ammo.put("PRIMARY", (short) 200);
        ammo.put("SECONDARY", (short) 200);
        player.setAmmo(ammo);
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


    //Methoden

    /**
     * Diese Methode aktualisiert den Spieler und verwaltet die Inputs der Maus und Tastatur im Spiel.
     * 
     * @param input Mouse- und Keyboard-Input
     * @param playerSpeed //TODO: remove
     * @param delta  Millisekunden seit dem letzten Frame
     * @param container GameContainer des Games
     * @param weaponController //TODO: static machen und dann als parameter entfernen
     */
    public void update(Input input, int delta, GameContainer container,
            WeaponController weaponController) {

        // Timervariablen
        if (reloadTimer > 0) {
            reloadTimer--;//TODO: im WeaponController implementieren
        }

        // Spielerbewegung
        float playerSpeed = player.getMovementSpeed();

        if (input.isKeyDown(Input.KEY_W) && player.getShape().getY() > 0) {
            player.setY(player.getShape().getY() - playerSpeed * delta);
        }
        if (input.isKeyDown(Input.KEY_S)
                && player.getShape().getY() < container.getHeight() - player.getShape().getHeight()) {
            player.setY(player.getShape().getY() + playerSpeed * delta);
        }
        if (input.isKeyDown(Input.KEY_A) && player.getShape().getX() > 0) {
            player.setX(player.getShape().getX() - playerSpeed * delta);
        }
        if (input.isKeyDown(Input.KEY_D)
                && player.getShape().getX() < container.getWidth() - player.getShape().getWidth()) {
            player.setX(player.getShape().getX() + playerSpeed * delta);
        }

        // Weapon Slot wechseln
        if (input.isKeyDown(Input.KEY_1)) {
            player.setEquipped(true);
        }
        if (input.isKeyDown(Input.KEY_2)) {
            player.setEquipped(false);
        }

        // Kugeln schießen
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            player.getEquippedWeapon().attack();
            if (player.getEquippedWeapon().getBullets() > 0) {
                weaponController.shoot(input, this.player);
            }
        }

        // Waffe nachladen
        if (input.isKeyDown(Input.KEY_R) && reloadTimer == 0) {
            player.reload();
            reloadTimer = player.getEquippedWeapon().getReloadRate();//TODO: im WeaponController implementieren
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
