package Controllers;

import java.util.HashSet;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Entities.Enemy;
import Entities.GuardianEnemy;
import Entities.SentinelEnemy;
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
	
	/**
	 * 
	 * @param x spawn coordinate on the x-axis
	 * @param y spawn coordinate on the y-axis
	 * @param d starting direction where the sentinel is looking at
	 * @throws SlickException 
	 */
	public static void createSentinel(int x, int y, float d) throws SlickException {
		SentinelEnemy s = new SentinelEnemy("assets/enemySprites/sentinel.png", x, y, d);
		EnemyController.addEnemy(s);
	}
	
	public static void createGuardian(int x, int y, float d) throws SlickException {
		GuardianEnemy g = new GuardianEnemy("assets/enemySprites/guardian.png", x, y, d);
		EnemyController.addEnemy(g);
	}
	
	public static void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}
	
	public static void update(GameContainer container, int delta) {
		Iterator<Enemy> it = enemies.iterator();
		while (it.hasNext()) {
			Enemy enemy =  it.next();
			if (enemy.getHitpoints()<=0) {
				it.remove();
				continue;
			}

			// shoot start

//			check if magazine is empty and whether reload timer needs to be set off
			Weapon equippedWeapon = enemy.getEquippedWeapon();

			if (equippedWeapon.getBullets() > 0) {
				WeaponController.shoot(enemy, null, null);
			} else if (equippedWeapon.getReloadTimer()==0){
				equippedWeapon.setReloadTimer(equippedWeapon.getReloadRate());
			}
			
			if (equippedWeapon.getReloadTimer()==1) {
				equippedWeapon.reload((equippedWeapon.getMagazineSize()));
			}

			// shoot end
			
			enemy.alignWithPlayer(delta);
			
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
