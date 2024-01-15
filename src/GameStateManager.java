import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import Controllers.WeaponController;
import Controllers.PlayerController;
import Entities.LivingEntity;
import Entities.Player;
import Level.Level;
import java.util.HashSet;

/**
 * Diese Klasse verwaltet den Spielzustand und steuert den Ablauf des Spiels.
 * Diese Klasse erbt von 'Basic Game' und implementiert die grundlegenden Funktionen für das Spiel.
 * 
 * Diese Klasse enthält Methoden zur Initialisierung, Aktualisierung und Darstellung des Spiels.
 * 
 * @author Sascha Angermann
 */
public class GameStateManager extends BasicGame {


    //Attribute

    //TODO: Controller static machen und unnötige Werte entfernen
    private HashSet<LivingEntity> livingEntities = new HashSet<LivingEntity>();
    private final float BULLET_SPEED  = 0.69f;//TODO: remove
    private Level mapAsset;
    private PlayerController playerController;//TODO: make static


    //Konstruktoren

    /**
     * Methode zur Initialisierung der GameStateManager-Klasse und der aus der Slice2D-Library vererbten Oberklasse BasicGame
     * 
     * @param title Titel des Spiels
     */
    public GameStateManager(String title) {
        super(title);
    }


    //Getter


    //Setter


    //Methoden

    /**
     * Methode zur Festlegung aller Konfigurationseinstellungen des Games, wie Fenstergröße und Framerate
     * 
     * @param args Befehlszeilenargumente
     * @throws SlickException Falls ein Fehler bei der Initialisierung auftritt.
     */
    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(
                    new GameStateManager("ayo voll krasses game alter check this out!"));
            app.setIcons(new String[] { "assets/appIcon.png" });
            app.setDisplayMode(800, 600, false);
            app.setTargetFrameRate(60);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode zur Initialisierung der Attribute der GameStateManager-Klasse.
     * 
     * @param container Game Container des Games
     * @throws SlickException Falls ein Fehler bei der Initialisierung auftritt.
     */
    @Override
    public void init(GameContainer container) throws SlickException {
        playerController = new PlayerController(container);
        livingEntities.add(playerController.getPlayer());
        mapAsset = new Level("assets/mapTest1.png");
    }

    /**
     * Methode zum Updaten aller logischen Elemente des Games, die sich verändern sollen.
     * Diese Methode wird mit jedem Frame von der GameContainer-Klasse aufgerufen.
     * 
     * @param container Game Container des Games
     * @param delta Millisekunden seit dem letzten Frame
     * @throws SlickException Falls ein Fehler beim Aktualisieren auftritt.
     */
    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        Input input = container.getInput();

        //Spieler updaten
        playerController.update(input, delta, container);

        //Waffen updaten
        WeaponController.update(input, BULLET_SPEED, delta, container, playerController.getPlayer());
    }

    /**
     * Methode zur Visualisierung aller Elemente des Games, die dargestellt werden sollen.
     * Diese Methode wird mit jedem Frame von der GameContainer-Klasse aufgerufen.
     * 
     * @param container Game Container des Games
     * @param g Grafische Darstellung des Spiels durch die Slick2D-Library
     * @throws SlickException Falls ein Fehler beim Darstellen auftritt
     */
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {

        //Map rendern
        mapAsset.render(g);

        //Weapons rendern
        WeaponController.render(g);

        //Player rendern
        playerController.render(g);

        //Draw HUD
        Player player = playerController.getPlayer();
        g.setColor(player.getEquippedWeapon().getReloadTimer() == 0 ? Color.orange : Color.red);
        if (player.getChangeEquippedWeaponTimer() != 0) {
            g.setColor(Color.gray);
        }
        g.drawString("Current Ammo: " + player.getEquippedWeapon().getBullets(), 10, container.getHeight() - 20);
        g.drawString("Ammo in Inventory: " + player.getAmmo().get(player.getEquippedWeapon().getAmmoType()), 10,
                container.getHeight() - 40);
        g.drawString("HP: " + player.getHitpoints(), 10, container.getHeight() - 60);
    }
    
}