package Controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;

import Entities.Enemy;
import Entities.Player;
import Entities.Weapon;
import Entities.Enemies.Computer;
import Entities.Enemies.GuardianEnemy;
import Entities.Enemies.SentinelEnemy;

/**
 * Diese Klasse verwaltet alle Gegner im Game.
 * 
 * @author Jeremy Adam
 */
public abstract class EnemyController {
	
	private static HashSet<Enemy> enemies;
	private static ArrayList<Computer> computers;
	private static Random random = new Random();
	
	// methods
	/**
	 * Adds an enemy to HashSet enemies containing all enemies in the game
	 * @param enemy Enemy to add to HashSet enemies
	 */
	public static void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}

	/**
	 * Adds a computer to HashSet computer containing all computers in the game
	 * @param enemy Computer to add to HashSet computers
	 */
	public static void addComputer(Computer computer) {
		computers.add(computer);
	}

	public static void init() {
		enemies = new HashSet<Enemy>();
		computers = new ArrayList<Computer>();
	}
	
	/**
	 * 
	 * @param x spawn coordinate on the x-axis
	 * @param y spawn coordinate on the y-axis
	 * @param d starting direction where the sentinel is looking at
	 * @throws SlickException 
	 */
	public static void createSentinel(float x, float y, float d) throws SlickException {
		SentinelEnemy s = new SentinelEnemy("assets/enemySprites/sentinel.png", x, y, d);
		EnemyController.addEnemy(s);
	}
	
	public static void createGuardian(float x, float y, float d) throws SlickException {
		GuardianEnemy g = new GuardianEnemy("assets/enemySprites/guardian.png", x, y, d);
		EnemyController.addEnemy(g);
	}

	public static void createComputer(float x, float y, float d, float spawnRangeX, float spawnRangeY) throws SlickException {
		Computer c = new Computer(x, y, spawnRangeX, spawnRangeY);
		EnemyController.addComputer(c);
		createSentinel(x, y, d);
		createSentinel(x, y, 90);
		createSentinel(x, y, 180);
		createGuardian(x, y, -90);
	}
	
	public static void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}
	
	public static void update(GameContainer container, int delta) {
		Iterator<Enemy> it = enemies.iterator();
		while (it.hasNext()) {
			Enemy enemy = it.next();
			if (enemy.getHitpoints() <= 0) {
				it.remove();
				continue;
			}

			// shoot start

			//			check if magazine is empty and whether reload timer needs to be set off
			Weapon equippedWeapon = enemy.getEquippedWeapon();

			//Enemy does not notice presence if player is too far away
			boolean playerIsVisible = true;
			Player player = PlayerController.getPlayer();
			Line distanceToPlayer = new Line(enemy.getShape().getCenterX(), enemy.getShape().getCenterY(),
					player.getShape().getCenterX(), player.getShape().getCenterY());
			if (distanceToPlayer.length() > enemy.getVisibilityRadius()) {
				playerIsVisible = false;
			}

			if (equippedWeapon.getBullets() > 0 && playerIsVisible) {
				WeaponController.shoot(enemy, null, null);
			} else if (equippedWeapon.getBullets() == 0 && equippedWeapon.getReloadTimer() == 0) {
				equippedWeapon.setReloadTimer(equippedWeapon.getReloadRate());
			}

			if (equippedWeapon.getReloadTimer() == 1) {
				equippedWeapon.reload((equippedWeapon.getMagazineSize()));
			}

			// shoot end
			if (playerIsVisible) {
				enemy.alignWithPlayer(delta);
				enemy.move(delta);
			}

			enemy.getDamageAnimation().update();
		}
		
		for (Computer computer : computers) {
			if (computer.getHitpoints() <= 0) {
				computer.die();
			}
			
			computer.getDamageAnimation().update();
		}
	}
	
	public static void render(Graphics g) {
		for (Enemy enemy : enemies) {
			enemy.render(g);
			enemy.getDamageAnimation().render(g);
		}

		for (Computer computer : computers) {
			computer.render(g);
			computer.getDamageAnimation().render(g);
		}
	}
	
	/**
	 * Spawn enemies around computers
	 * 
	 * @throws SlickException
	 */
	public static void spawnEnemiesAroundComputers() throws SlickException {

		//values to be decided by amount of undamaged computers
		short sentinelAmount = 0;
		short guardianAmount = 0;
		short maxAmountOfEnemies = 0;

		//change values
		switch (getAmountOfComputers()) {
			case 4:
				sentinelAmount = 3;
				guardianAmount = 1;
				maxAmountOfEnemies = 10;
				break;
			case 3:
				sentinelAmount = 4;
				guardianAmount = 2;
				maxAmountOfEnemies = 15;
				break;
			case 2:
				sentinelAmount = 5;
				guardianAmount = 4;
				maxAmountOfEnemies = 20;
				break;
			case 1:
				sentinelAmount = 2;
				guardianAmount = 6;
				maxAmountOfEnemies = 25;
				break;
			default:
				break;
		}

		//spawn sentinels
		for (int i = 0; i < sentinelAmount; i++) {

			//do not spawn any enemies if limit has been reached
			if (enemies.size() >= maxAmountOfEnemies) {
				return;
			}

			//choose random undamaged computer to spawn enemies around at
			Computer computer;
			do {
				computer = computers.get(random.nextInt(computers.size()));
			} while (computer.isDestroyed());

			//choose random coordinates that are not inside a wall
			float randomX;
			float randomY;

			float maxX = computer.getShape().getCenterX() + computer.getSpawnRangeX();
			float maxY = computer.getShape().getCenterY() + computer.getSpawnRangeY();
			float minX = computer.getShape().getCenterX() - computer.getSpawnRangeX();
			float minY = computer.getShape().getCenterY() - computer.getSpawnRangeY();

			do {
				
				randomX = random.nextFloat(minX, maxX);
				randomY = random.nextFloat(minY, maxY);
			} while (LevelController.getIsHittingCollision(randomX, randomY));

			//create sentinal at these coordinates
			createSentinel(randomX, randomY, 0);
		}
		
		//spawn guardians
		for (int i = 0; i < guardianAmount; i++) {

			//do not spawn any enemies if limit has been reached
			if (enemies.size() >= maxAmountOfEnemies) {
				return;
			}

			//choose random undamaged computer to spawn enemies around at
			Computer computer;
			do {
				computer = computers.get(random.nextInt(computers.size()));
			} while (computer.isDestroyed());

			//choose random coordinates that are not inside a wall
			float randomX;
			float randomY;
			do {
				randomX = computer.getShape().getCenterX() + (random.nextFloat() - 0.5f) * 500;
				randomY = computer.getShape().getCenterY() + (random.nextFloat() - 0.5f) * 500;	
			} while (LevelController.getIsHittingCollision(randomX, randomY));

			//create guardian at these coordinates
			createGuardian(randomX, randomY, 0);
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

	/**
	 * returns all computers
	 * @return
	 */
	public static ArrayList<Computer> getComputers() {
		return computers;
	}

	/**
	 * returns number of working computers
	 * 
	 * @return number of working computers
	 */
	public static short getAmountOfComputers() {
		short amount = 0;

		for (Computer computer : computers) {
			if (!computer.isDestroyed()) {
				amount++;
			}
		}

		return amount;
	}
	
	
}
