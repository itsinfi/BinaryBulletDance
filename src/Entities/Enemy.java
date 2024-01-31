package Entities;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import Entities.Animations.BulletFireAnimation;
import Controllers.LevelController;
import Controllers.PlayerController;

import java.lang.Math;
import java.util.HashMap;
import java.util.Random;

public abstract class Enemy extends LivingEntity{
	
	protected float targetPosX;
	protected float targetPosY;
	protected float rotationSpeed;
	protected float visibilityRadius = 1000;
	

	/**
	 * creates enemy
	 * 
	 * @param spriteAsset asset for sprite of enemy
	 * @param centerX center x coordinate
	 * @param centerY center y coordinate
	 * @param direction direction in degrees
	 * @throws SlickException if asset could not be found
	 */
	public Enemy(String spriteAsset, float centerX, float centerY, float direction) throws SlickException {
		super(spriteAsset, centerX, centerY, direction);
		this.targetPosX = this.getShape().getX();
		this.targetPosY = this.getShape().getY();
	}


	/**
	 * This method returns the visibility radius of the enemy
	 * 
	 * @return Visibility radius of the enemy
	 */
	public float getVisibilityRadius() {
		return visibilityRadius;
	}

	
	/**
	 * moves enemy towards a randomly selected target position within a certain distance near the player
	 * @param delta Millisekunden seit dem letzten Frame
	 */
	
	public void move(int delta) {
		
		float enemyX = this.getShape().getCenterX();
		float enemyY = this.getShape().getCenterY();
		
		float playerX = PlayerController.getPlayer().getShape().getCenterX();
		float playerY = PlayerController.getPlayer().getShape().getCenterY();
		
		float distanceX = Math.abs(enemyX - targetPosX);
		float distanceY = Math.abs(enemyY - targetPosY);
		

		
		// how far the target point from the enemy can be next to player
		float range = 500;
		// how close the enemy needs to get to target point before generating a new one
		// needs to be above 0 or else the enemy will only approximately reach the target and never generate a new one
		float maxDistance = 10;
		
		Random random = new Random();
		
		// generate a new target point until target point is not in collision area
//		do {
		float minX = playerX-range;
		float maxX = playerX+range;
		
		float minY = playerY - range;
		float maxY = playerY + range;
		
		if (distanceX < maxDistance) {
			
			this.targetPosX = random.nextFloat(minX, maxX);
			
		}
		
		if (distanceY < maxDistance) {

			this.targetPosY = random.nextFloat(minY, maxY);

		}
		
		
		//bullet fire for weapon when shooting
		BulletFireAnimation bulletFireAnimation = equippedWeapon.getBulletFire();

		//control enemy movement on x axis
		if (enemyX > targetPosX) {
			
			if (!(LevelController.getIsHittingCollision(enemyX-this.movementSpeed * delta, enemyY))) {
				this.setX(this.getShape().getX() - this.movementSpeed * delta);
				equippedWeapon.setX(equippedWeapon.getShape().getX() - this.movementSpeed * delta);
				bulletFireAnimation.setX(bulletFireAnimation.getShape().getX() - this.movementSpeed * delta);
				damageAnimation.setX(damageAnimation.getShape().getX() - this.movementSpeed * delta);
			} else {
				this.targetPosX = random.nextFloat(enemyX-25, enemyX+25);
				this.targetPosY = random.nextFloat(enemyY-25, enemyY+25);
			}
			
		} else if (enemyX < targetPosX) {
			if (!(LevelController.getIsHittingCollision(enemyX + this.movementSpeed * delta, enemyY))) {
				this.setX(this.getShape().getX() + this.movementSpeed * delta);
				equippedWeapon.setX(equippedWeapon.getShape().getX() + this.movementSpeed * delta);
				bulletFireAnimation.setX(bulletFireAnimation.getShape().getX() + this.movementSpeed * delta);
				damageAnimation.setX(damageAnimation.getShape().getX() + this.movementSpeed * delta);
			} else {
				this.targetPosX = random.nextFloat(enemyX - 25, enemyX + 25);
				this.targetPosY = random.nextFloat(enemyY - 25, enemyY + 25);
			}

		}

		//control enemy movement on y axis
		if (enemyY > targetPosY) {
			if (!(LevelController.getIsHittingCollision(enemyX, enemyY-this.movementSpeed * delta))) {
				this.setY(this.getShape().getY() - this.movementSpeed * delta);
				equippedWeapon.setY(equippedWeapon.getShape().getY() - this.movementSpeed * delta);
				bulletFireAnimation.setY(bulletFireAnimation.getShape().getY() - this.movementSpeed * delta);
				damageAnimation.setY(damageAnimation.getShape().getY() - this.movementSpeed * delta);
			} else {
				this.targetPosX = random.nextFloat(enemyX-25, enemyX+25);
				this.targetPosY = random.nextFloat(enemyY-25, enemyY+25);
			}
			
		} else if (enemyY < targetPosY) {
			if (!(LevelController.getIsHittingCollision(enemyX, enemyY+this.movementSpeed * delta))) {
				this.setY(this.getShape().getY() + this.movementSpeed * delta);
				equippedWeapon.setY(equippedWeapon.getShape().getY() + this.movementSpeed * delta);
				bulletFireAnimation.setY(bulletFireAnimation.getShape().getY() + this.movementSpeed * delta);
				damageAnimation.setY(damageAnimation.getShape().getY() + this.movementSpeed * delta);
			} else {
				this.targetPosX = random.nextFloat(enemyX-25, enemyX+25);
				this.targetPosY = random.nextFloat(enemyY-25, enemyY+25);
			}
			
		}

	}
	
	/**
	 * aligns Enemy automatically with player
	 */
	public void alignWithPlayer(int delta) {
		float enemyX = this.getShape().getCenterX();
		float enemyY = this.getShape().getCenterY();

		float playerX = PlayerController.getPlayer().getShape().getCenterX();
		float playerY = PlayerController.getPlayer().getShape().getCenterY();

		Vector2f enemyDirection = new Vector2f(playerX - enemyX, playerY - enemyY).normalise();
		float targetRotationAngle = (float) Math.toDegrees(Math.atan2(enemyDirection.getY(), enemyDirection.getX()));

		float enemyRotationAngle;

		int turnDirection = turnLeftOrRight(this.direction, targetRotationAngle);
		if (turnDirection > 0) {
			enemyRotationAngle = this.direction + this.rotationSpeed * delta;
		} else if (turnDirection < 0) {
			enemyRotationAngle = this.direction - this.rotationSpeed * delta;
		} else {
			enemyRotationAngle = targetRotationAngle;
		}

		this.setDirection(enemyRotationAngle);
		damageAnimation.setDirection(enemyRotationAngle);
		equippedWeapon.setDirection(enemyRotationAngle);
		equippedWeapon.getBulletFire().setDirection(enemyRotationAngle);
	}
	
	/**
	 * turns enemy left or right based on player position
	 * 
	 * @param currentDirection
	 * @param targetDirection
	 * @return whether to turn left (-1) or right (1) or none (0)
	 */
	protected int turnLeftOrRight(float currentDirection, float targetDirection) {
		//		Quelle: ChatGPT

		float angleDifference = targetDirection - currentDirection;
		angleDifference = (angleDifference + 180) % 360 - 180;
		if (angleDifference > 0) {
			return 1; // Rotate right is shorter
		} else if (angleDifference < 0) {
			return -1; // Rotate left is shorter
		} else {
			return 0; // Angles are the same
		}
	}
	
	/**
	 * reload weapon
	 */
	public void reload() {
		short requiredBullets = (short) (this.equippedWeapon.getMagazineSize() - this.equippedWeapon.getBullets());
		this.equippedWeapon.reload(requiredBullets);
	}

	/**
	 * gives player animation upon death
	 */
	@Override
	public void die() {
		// replenish ammo for player when defeated
		HashMap<String, Short> a = new HashMap<String, Short>();
		a.put(this.equippedWeapon.getAmmoType(), this.equippedWeapon.getMagazineSize());
		PlayerController.getPlayer().addAmmo(a);
	}

	/**
	 * sets equippedWeapon of enemy
	 * 
	 * @param w weapon
	 */
	public void setEquippedWeapon(Weapon w) {
		this.equippedWeapon = w;
	}
}
