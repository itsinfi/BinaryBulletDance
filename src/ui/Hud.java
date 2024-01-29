package ui;

import java.awt.Font;
import java.io.InputStream;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import Controllers.MusicController;
import Controllers.PlayerController;
import Entities.Player;

/**
 * Diese Klasse ist für die Darstellung der HUD-Darstellung verantwortlich.
 */

public abstract class Hud {
	
	// Attributes
	 private static InputStream inputStream;
     private static Font awtFont;
     private static TrueTypeFont font;
     
     private static Font gameOverFont;
     private static TrueTypeFont gameOverTTF;

     private static Random random = new Random();
     private static Sound deathDialog;
     
    // getter and setter
	
	public static InputStream getInputStream() {
		return inputStream;
	}

	public static Font getAwtFont() {
		return awtFont;
	}

	public static TrueTypeFont getFont() {
		return font;
	}
	
	public static void render(Graphics g, GameContainer container) {
		g.setFont(Hud.getFont());
        
        Player player = PlayerController.getPlayer();

        if (player.getEquippedWeapon() != null) {
            g.setColor(player.getEquippedWeapon().getReloadTimer() == 0 ? Color.orange : Color.red);
            if (player.getChangeEquippedWeaponTimer() != 0) {
                g.setColor(Color.gray);
            }
            g.drawString("Current Ammo: " + player.getEquippedWeapon().getBullets(), 10, container.getHeight() - 20);
            g.drawString(
                    "Ammo in Inventory: " + (!player.getEquippedWeapon().getInfiniteAmmo()
                            ? player.getAmmo().get(player.getEquippedWeapon().getAmmoType()) == null ? 0 : player.getAmmo().get(player.getEquippedWeapon().getAmmoType())
                            : "unforseeable."),
                    10,
                    container.getHeight() - 40);
        }
        
        g.drawString("HP: " + player.getHitpoints(), 10, container.getHeight() - 60);
        
        // used for testing
        
        g.drawString("X: " + player.getShape().getCenterX(), 200, 200);
        g.drawString("Y: "+ player.getShape().getCenterY(), 200, 180);
        
        // Game Over Screen
        if (PlayerController.getPlayer().getHitpoints() <= 0) {
            MusicController.stopMusic();
        	gameOverScreen(g, container);
        }
        
	}

	/**
	 * initializes attributes of Hud
	 */
     public static void init() {
    	 try {
             InputStream inputStream = ResourceLoader.getResourceAsStream("assets/nasalization-rg.ttf");
             Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
             awtFont = awtFont.deriveFont(12f); // Set the font size
             font = new TrueTypeFont(awtFont, true);
             
             gameOverFont = awtFont.deriveFont(50f);
         	
             gameOverTTF = new TrueTypeFont (gameOverFont, true);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
	
	// Methods

     /**
      * Displays a Game Over Screen
      * @param g for modifying font color
      * @param container for drawing Strings
      */
    public static void gameOverScreen(Graphics g, GameContainer container) {
        	g.setFont(gameOverTTF);
        	
        	g.setColor(Color.red);
        	
        	g.drawString("You died!", (container.getWidth()-250)/2, (container.getHeight()-100)/2);
            g.drawString("Press E to exit!", (container.getWidth() - 350) / 2, (container.getHeight() + 25) / 2);
            
            playDeathDialogSound();
    	
    }
    
    /**TODO:
     * Diese Methode spielt den Sound, wenn der Spieler stirbt (ein zufälliger aus 4)
     */
    public static void playDeathDialogSound() {
        if (deathDialog == null) {
        //     int randomInt = random.nextInt(4) + 1;
        //     String fileName = "assets/sounds/death_dialog_" + randomInt + ".wav";
        //     try {
        //         deathDialog = new Sound(fileName);
        //         deathDialog.play();
        //     } catch (SlickException e) {
        //         System.out.println(e);
        //     }
        }
    }
}
