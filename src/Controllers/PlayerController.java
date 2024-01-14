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
    private short reloadTimer = 0;//TODO: im WeaponController implementieren


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

        //Primärwaffe des Spielers erzeugen
        Weapon primary = new Weapon(false, (short) 30, "PRIMARY", (short) 5, 0.0, (short) 200, (short) 30,
                (short) 30, (short) 120, true);

        //Sekundärwaffe des Spielers erzeugen
        Weapon secondary = new Weapon(true, (short) 30, "SECONDARY", (short) 15, 0.0, (short) 200, (short) 15,
                (short) 15, (short) 1000, false);
        
        //Primärwaffe dem WeaponController übergeben
        WeaponController.addWeapon(primary);

        //Sekundärwaffe dem WeaponController übergeben
        WeaponController.addWeapon(secondary);
        
        //Spieler erzeugen
        player = new Player("assets/playertest.png", container, primary, primary, secondary);

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


    //Methoden

    /**
     * Diese Methode aktualisiert den Spieler und verwaltet die Inputs der Maus und Tastatur im Spiel.
     * 
     * @param input Mouse- und Keyboard-Input
     * @param playerSpeed //TODO: remove
     * @param delta  Millisekunden seit dem letzten Frame
     * @param container GameContainer des Games
     */
    public void update(Input input, int delta, GameContainer container) {

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

        // Ausgerüstete Waffe lesen
        Weapon equippedWeapon = player.getEquippedWeapon();

        //Prüfen, ob die Waffe nachgeladen wird oder bereits den nächsten Schuss abgeben darf
        if (equippedWeapon.getReloadTimer() == 0 && equippedWeapon.getFireTimer() == 0) {

            //Prüfen, ob die Waffe automatisch ist und ob die linke Maustaste gedrückt (bzw. gehalten bei automatisch) wurde
            if ((equippedWeapon.getAutomaticFire() && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
                    || (!equippedWeapon.getAutomaticFire() && input.isMousePressed(Input.MOUSE_LEFT_BUTTON))) {
                
                //Waffe schießen
                if (player.getEquippedWeapon().getBullets() > 0) {
                    WeaponController.shoot(input, this.player);
                }
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
