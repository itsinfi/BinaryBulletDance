package Controllers;

import java.util.HashSet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import Entities.Enemy;

/**
 * Diese Klasse verwaltet alle Gegner im Game.
 * 
 * @author Jeremy Adam
 */
public class EnemyController {
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
	
	public static void update(GameContainer container) {
		for (Enemy enemy : enemies) {
			enemy.alignWithPlayer();
		}
	}
	
	public static void render(Graphics g) {
		for (Enemy enemy : enemies) {
			enemy.render(g);
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
