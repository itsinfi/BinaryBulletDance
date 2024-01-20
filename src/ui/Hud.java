package ui;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

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
        
        g.setColor(player.getEquippedWeapon().getReloadTimer() == 0 ? Color.orange : Color.red);
        if (player.getChangeEquippedWeaponTimer() != 0) {
            g.setColor(Color.gray);
        }
        g.drawString("Current Ammo: " + player.getEquippedWeapon().getBullets(), 10, container.getHeight() - 20);
        g.drawString(
                "Ammo in Inventory: " + (!player.getEquippedWeapon().getInfiniteAmmo()
                        ? player.getAmmo().get(player.getEquippedWeapon().getAmmoType())
                        : "unforseeable."),
                10,
                container.getHeight() - 40);
        g.drawString("HP: " + player.getHitpoints(), 10, container.getHeight() - 60);
        
        // Game Over Screen
        if(PlayerController.getPlayer().getHitpoints() <= 0) {
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
        	g.drawString("Press E to exit!", (container.getWidth()-350)/2, (container.getHeight()+25)/2);
    	
    }

}
