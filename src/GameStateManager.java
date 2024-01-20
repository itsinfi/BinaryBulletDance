import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import Controllers.WeaponController;
import Controllers.PlayerController;
import Entities.LivingEntity;
import Entities.Player;
import Level.Level;
import ui.Hud;

import java.io.InputStream;
import java.util.HashSet;
import java.awt.Font;

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
        PlayerController.init(container);
        livingEntities.add(PlayerController.getPlayer());
        mapAsset = new Level("assets/mapTest1.png");

        //Font für die GUI laden        
        Hud.init();
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
        Player player = PlayerController.getPlayer();
        
        // stop game when player is dead
    	if (player.getHitpoints()<=0) {
    		
    		if (input.isKeyDown(Input.KEY_E)) {
    			container.exit();
    		} else {
    			return;
    		}
    	}

        //Spieler updaten
        PlayerController.update(input, delta, container);

        //Waffen updaten
        WeaponController.update(input, BULLET_SPEED, delta, container, PlayerController.getPlayer());
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
        PlayerController.render(g);

        //Draw HUD
        Hud.render(g, container);
        
    }
    
}