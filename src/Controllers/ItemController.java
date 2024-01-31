package Controllers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Entities.Player;
import Entities.Items.Medkit;

/**
 * Diese Klasse verwaltet alle Items im Game.
 * 
 * @author Hendrik Gomez
 */
public final class ItemController {

    //attributes
    private static HashSet<Medkit> medkits;
    private static short limitOfMedkits = 15;


    //constructors
    private ItemController() {
        throw new AssertionError();
    }


    //methods

    /**
     * initialize all items
     * 
     * @throws SlickException
     */
    public static void init() throws SlickException {
        medkits = new HashSet<Medkit>();
        generateMedkits((short) 5);
    }

    /**
     * render all items
     * 
     * @param g graphics from slick2d
     */
    public static void render(Graphics g) {
        Iterator<Medkit> it = medkits.iterator();
        while (it.hasNext()) {
            Medkit medkit = it.next();
            medkit.render(g);
        }
    }

    /**
     * update all items
     */
    public static void update() {
        Player player = PlayerController.getPlayer();

        Iterator<Medkit> it = medkits.iterator();
        while (it.hasNext()) {
            Medkit medkit = it.next();

            if (player.getShape().intersects(medkit.getShape())) {
                medkit.pickUp();
                it.remove();
            }
        }
    }
    
    /**
     * generate new medkits
     * 
     * @param amount number of medkits to be spawned
     * 
     * @throws SlickException for errors when creating a new medkit
     */
    public static void generateMedkits(short amount) throws SlickException {

        //for random values for coordinates
        Random random = new Random();

        //create medkits
        for (int i = 0; i < amount; i++) {

            //spawn no medkits if limit has been reached
            if (medkits.size() > limitOfMedkits) {
                return;
            }
            
            //generate random coordinates that are not stuck in a wall
            float xPos = 0;
            float yPos = 0;
            do  {
                xPos = random.nextFloat() * LevelController.getLevelWidth();
                yPos = random.nextFloat() * LevelController.getLevelHeight();
            } while (xPos < 0 && yPos < 0 && LevelController.getIsHittingCollision(xPos, yPos));

            //create new medkit and add it to hashset of all medkits
            Medkit medkit = new Medkit(xPos, yPos);
            medkits.add(medkit);
        }
    }
}
