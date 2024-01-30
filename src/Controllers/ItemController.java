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
public abstract class ItemController {

    private static HashSet<Medkit> medkits;


    public static void init() throws SlickException {
        medkits = new HashSet<Medkit>();
        generateMedkits((short) 5);
    }

    public static void render(Graphics g) throws SlickException {
        Iterator<Medkit> it = medkits.iterator();
        while (it.hasNext()) {
            Medkit medkit = it.next();
            medkit.render(g);
        }
    }

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
    

    public static void generateMedkits(short amount) throws SlickException {

        //Maximal 15 Medkits erlauben
        if (medkits.size() > 15) {
            return;
        }

        Random random = new Random();

        float xPos = 0;
        float yPos = 0;

        for (int i = 0; i < amount; i++) {
            
            do  {
                xPos = random.nextFloat() * LevelController.getLevelWidth();
                yPos = random.nextFloat() * LevelController.getLevelHeight();
            } while (xPos < 0 && yPos < 0 && LevelController.getIsHittingCollision(xPos, yPos));

            Medkit medkit = new Medkit(xPos, yPos);
            medkits.add(medkit);
        }
    }
}
