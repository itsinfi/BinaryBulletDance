package Controllers;

import java.util.HashSet;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import Entities.Enemy;
import Entities.Weapon;

/**
 * Diese Klasse verwaltet alle Gegner im Game.
 * 
 * @author Jeremy Adam
 */
public abstract class EnemyController {
    //TODO:
	
	private static HashSet<Enemy> enemies = new HashSet<Enemy>();
	
	// methods
	/**
	 * Adds an enemy to HashSet enemies containing all enemies in the game
	 * @param enemy Enemy to add to HashSet enemies
	 */
	public static void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}
	
	public static void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}
	
	public static void update(GameContainer container, int delta) {
		Iterator<Enemy> it = enemies.iterator();
		while (it.hasNext()) {
			Enemy enemy =  it.next();
			if (enemy.getHitpoints()<=0) {
				enemy.die();
				it.remove();
				continue;
			}
			enemy.alignWithPlayer();

			// shoot start
	        WeaponController.shoot(enemy);
			// shoot end
			
//			WeaponController.shoot(enemy);
			enemy.move(delta);
			enemy.getDamageAnimation().update();
		}
	}
	
	public static void render(Graphics g) {
		for (Enemy enemy : enemies) {
			enemy.render(g);
			g.drawString("HP: " + enemy.getHitpoints(), 200, 160);
			enemy.getDamageAnimation().render(g);
		}
		
	}
	
	// getter and setter
	/**
	 * returns all enemies
	 * @return
	 */
	public static HashSet<Enemy> getEnemies() {
		return enemies;
	}
	
	
}
