package Controllers;

import java.util.HashSet;

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
	
	public static void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}
	
	
}
