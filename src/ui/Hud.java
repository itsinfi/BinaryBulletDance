package ui;

import java.awt.Font;
import java.io.InputStream;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import Controllers.EnemyController;
import Controllers.LevelController;
import Controllers.MusicController;
import Controllers.PlayerController;
import Entities.Player;
import Level.Level;

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
        
        float maxWidth = LevelController.getCameraX();
        float maxHeight = LevelController.getCameraY();
        
        Player player = PlayerController.getPlayer();

        if (player.getEquippedWeapon() != null) {
            g.setColor(player.getEquippedWeapon().getReloadTimer() == 0 ? Color.orange : Color.red);
            if (player.getChangeEquippedWeaponTimer() != 0) {
                g.setColor(Color.gray);
            }
            g.drawString("Current Ammo: " + player.getEquippedWeapon().getBullets(), maxWidth + 15, maxHeight + 850);
            g.drawString(
                    "Ammo in Inventory: " + (!player.getEquippedWeapon().getInfiniteAmmo()
                            ? player.getAmmo().get(player.getEquippedWeapon().getAmmoType()) == null ? 0 : player.getAmmo().get(player.getEquippedWeapon().getAmmoType())
                            : "unforseeable."),
                    maxWidth + 15,
                    maxHeight + 870);
        }
        
        g.drawString("HP: " + player.getHitpoints(), maxWidth + 15, maxHeight + 830);
        
        // draw amount of computers destroyed
        Image intact;
        Image destroyed;
        
        Image c1;
        Image c2;
        Image c3;
        Image c4;
		try {
			
			intact = new Image("assets/enemySprites/computer.png");
			destroyed = new Image("assets/enemySprites/computer_broken.png");
			
			c1 = intact;
			c2 = intact;
			c3 = intact;
			c4 = intact;
			
			switch(EnemyController.getAmountOfComputers()) {
			case 3:
				c1 = destroyed;
				break;
			case 2:
				c1 = destroyed;
				c2 = destroyed;
				break;
			case 1:
				c1 = destroyed;
				c2 = destroyed;
				c3 = destroyed;
				break;
			case 0:
				c1 = destroyed;
				c2 = destroyed;
				c3 = destroyed;
				c4 = destroyed;
			}
			
			g.drawImage(c1, maxWidth+1500, maxHeight+1000);
			g.drawImage(c2, maxWidth+1600, maxHeight+1000);
			g.drawImage(c3, maxWidth+1700, maxHeight+1000);
			g.drawImage(c4, maxWidth+1800, maxHeight+1000);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
//        g.drawString("")
        
        // Win Screen
        if (EnemyController.getAmountOfComputers()==0) {
        	winScreen(g, container);
        }
        
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
        	
        	float maxWidth = LevelController.getCameraX();
            float maxHeight = LevelController.getCameraY();

            g.drawString("You died!", maxWidth + 835, maxHeight + 470);
            g.drawString("Press E to restart game!", maxWidth + 680, maxHeight + 520);
            g.drawString("Press Escape to close.", maxWidth + 680, maxHeight + 570);
            
            
            playDeathDialogSound();
    	
    }
    
    public static void winScreen(Graphics g, GameContainer container) {
    	g.setFont(gameOverTTF);
    	g.setColor(Color.green);
    	
    	float maxWidth = LevelController.getCameraX();
        float maxHeight = LevelController.getCameraY();

        g.drawString("You won!", maxWidth + 835, maxHeight + 470);
        g.drawString("Press E to restart game!", maxWidth + 680, maxHeight + 520);
        g.drawString("Press Escape to close.", maxWidth + 680, maxHeight + 570);
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
