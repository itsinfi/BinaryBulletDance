import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import Controllers.WeaponController;
import Controllers.EnemyController;
import Controllers.ItemController;
import Controllers.LevelController;
import Controllers.MusicController;
import Controllers.PlayerController;
import Entities.Player;
import ui.Hud;

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

    private short gameLoopTimer = 0;
    private short gameLoopRate = 60;
    private int lastKnownAmountOfComputers = 4;


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
            GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            AppGameContainer app = new AppGameContainer(
                    new GameStateManager("Binary Bullet Dance"));
            app.setIcons(new String[] { "assets/appIcon/appIcon.png" });
            app.setDisplayMode(graphicsDevice.getDisplayMode().getWidth(), graphicsDevice.getDisplayMode().getHeight(), false);
            app.setTargetFrameRate(60);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode zur Initialisierung der Attribute der GameStateManager-Klasse.
     * 
     * @param container container of the window
     * @throws SlickException Falls ein Fehler bei der Initialisierung auftritt.
     */
    @Override
    public void init(GameContainer container) throws SlickException {
        WeaponController.init();
        PlayerController.init(container);
        EnemyController.init();

        LevelController.loadLevel("01");

        ItemController.init();

        //Font für die GUI laden        
        Hud.init();
        
        // spawn computers
        EnemyController.createComputer(1155, 170, 0, 385, 1130);//Lager
        EnemyController.createComputer(4000, 390, 0, 1500, 410);//Villa
        EnemyController.createComputer(5850, 5440, 0, 530, 1500);//Mülldeponie
        EnemyController.createComputer(1400, 6000, 0, 570, 4000);//Büro

        MusicController.init();
        MusicController.startMusic();
    }

    /**
     * Methode zum Updaten aller logischen Elemente des Games, die sich verändern sollen.
     * Diese Methode wird mit jedem Frame von der GameContainer-Klasse aufgerufen.
     * 
     * @param container container of the window
     * @param delta Millisekunden seit dem letzten Frame
     * @throws SlickException Falls ein Fehler beim Aktualisieren auftritt.
     */
    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        Input input = container.getInput();
        Player player = PlayerController.getPlayer();

        //stop game when esc is pressed
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            container.exit();
        }
        
        // restart game when player is dead
        if (player.getHitpoints() <= 0 || EnemyController.getAmountOfComputers() <= 0) {
            if (input.isKeyDown(Input.KEY_E)) {
                MusicController.stopMusic();
                init(container);
            } else {
                return;
            }
        }

        //Game Loop updaten
        updateGameLoop();
        
        //Level updaten
        LevelController.update(container, delta);

        //Spieler updaten
        PlayerController.update(input, delta, container);

        //Waffen updaten
        WeaponController.update();
        
        //Gegner updaten
        EnemyController.update(container, delta);

        //Items updaten
        ItemController.update();
    }

    /**
     * Methode zur Visualisierung aller Elemente des Games, die dargestellt werden sollen.
     * Diese Methode wird mit jedem Frame von der GameContainer-Klasse aufgerufen.
     * 
     * @param container container of the window
     * @param g Grafische Darstellung des Spiels durch die Slick2D-Library
     * @throws SlickException Falls ein Fehler beim Darstellen auftritt
     */
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {

        //Kamera übersetzen
        LevelController.translateCamera(g);

        //Map rendern
        LevelController.render(g);

        //Weapons rendern
        WeaponController.render(g);

        //Gegner rendern
        EnemyController.render(g);

        //Player rendern
        PlayerController.render(g);

        //Items rendern
        ItemController.render(g);

        //Draw HUD
        Hud.render(g, container);

        //Kameraübersetzung aufheben
        // LevelController.resetCameraTranslation(g);

    }
    
    /**
     * Diese Methode verwaltet den GameLoop:
     * 
     * - Spiel beenden, sobald alle 4 Computer zerstört wurden
     * - Solange werden immer Medkits auf der gesamten Karte gespawnt
     * - Jeder Computer spawnt Gegner um sich herum
     * - Wird ein Computer zerstört, können mehr Gegner insgesamt gespawnt werden
     * - Ebenso erhöht sich die Spawnrate pro zerstörtem Computer
     */
    public void updateGameLoop() throws SlickException{

        int amountOfComputers = EnemyController.getAmountOfComputers();

        if (amountOfComputers == 0) {
            lastKnownAmountOfComputers = amountOfComputers;
            return;
        }

        if (lastKnownAmountOfComputers != amountOfComputers) {
            lastKnownAmountOfComputers = amountOfComputers;
            switch (amountOfComputers) {
                case 1:
                    gameLoopRate = 40;
                    break;
                case 2:
                    gameLoopRate = 45;
                    break;
                case 3:
                    gameLoopRate = 50;
                    break;
                default:
                    gameLoopRate = 60;
                    break;
            }
        }

        if (gameLoopTimer == 0) {

            ItemController.generateMedkits((short) 2);

            EnemyController.spawnEnemiesAroundComputers();

            gameLoopTimer = gameLoopRate;
        } else {
            gameLoopTimer--;
        }
    }
    
}