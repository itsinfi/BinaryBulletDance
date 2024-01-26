package Controllers;

import java.util.HashSet;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Entities.Enemy;
import Entities.Weapon;
import Entities.Animations.DamageAnimation;

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
			//Prüfen, ob die Waffe nachgeladen wird oder bereits den nächsten Schuss abgeben darf oder aktuell noch nicht fertig ausgerüstet ist.
			Weapon equippedWeapon = enemy.getEquippedWeapon();
	        if (equippedWeapon.getReloadTimer() == 0 && equippedWeapon.getFireTimer() == 0) {

	            //Prüfen, ob die Waffe automatisch ist und ob die linke Maustaste gedrückt (bzw. gehalten bei automatisch) wurde
	            if ((equippedWeapon.getAutomaticFire()) || (!equippedWeapon.getAutomaticFire())) {

	                //Waffe schießen
	                if (equippedWeapon.getBullets() > 0) {
	                    WeaponController.shoot(enemy);
	                } else {
	                	equippedWeapon.setReloadTimer(equippedWeapon.getReloadRate());
	                	equippedWeapon.reload((short)(15));
	                }
	            }
	        }
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
