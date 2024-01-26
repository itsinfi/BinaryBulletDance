package Entities;

import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import Entities.Weapon;
import Entities.Animations.BulletFireAnimation;
import Controllers.EnemyController;
import Controllers.PlayerController;
import Controllers.WeaponController;

import java.lang.Math;
import java.util.Random;

public abstract class Enemy extends LivingEntity{
	
	protected float targetPosX;
	protected float targetPosY;
	protected float rotationSpeed;
	

	public Enemy(String spriteAsset, float centerX, float centerY, float direction) throws SlickException {
		super(spriteAsset, centerX, centerY, direction);
		this.targetPosX = this.getShape().getX();
		this.targetPosY = this.getShape().getY();
		// TODO Auto-generated constructor stub
	}

    //TODO:

    

	/**
	 * 
	 */
    @Override
    public void die() {
    	
//    	throw new UnsupportedOperationException("Unimplemented method 'die'");
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
		
		float distanceX = Math.abs(enemyX-targetPosX);
		float distanceY = Math.abs(enemyY-targetPosY);
		
		float range = 150;
		float maxDistance = 10;
		
		Random random = new Random();
		
		if (distanceX < maxDistance) {
			
			float min = playerX-range;
			float max = playerX+range;
			
			this.targetPosX = random.nextFloat(min, max);
			
		}
		
		if (distanceY < maxDistance) {

			float min = playerY - range;
			float max = playerY + range;

			this.targetPosY = random.nextFloat(min, max);

		}
		
		BulletFireAnimation bulletFireAnimation = equippedWeapon.getBulletFire();

		if (enemyX > targetPosX) {
			this.setX(this.getShape().getX() - this.movementSpeed * delta);
			bulletFireAnimation.setX(bulletFireAnimation.getShape().getX() - this.movementSpeed * delta);
			damageAnimation.setX(damageAnimation.getShape().getX() - this.movementSpeed * delta);
		} else if (enemyX < targetPosX) {
			this.setX(this.getShape().getX() + this.movementSpeed * delta);
			bulletFireAnimation.setX(bulletFireAnimation.getShape().getX() + this.movementSpeed * delta);
			damageAnimation.setX(damageAnimation.getShape().getX() + this.movementSpeed * delta);
		}

		if (enemyY > targetPosY) {
			this.setY(this.getShape().getY() - this.movementSpeed * delta);
			bulletFireAnimation.setY(bulletFireAnimation.getShape().getY() - this.movementSpeed * delta);
			damageAnimation.setY(damageAnimation.getShape().getY() - this.movementSpeed * delta);
		} else if (enemyY < targetPosY) {
			this.setY(this.getShape().getY() + this.movementSpeed * delta);
			bulletFireAnimation.setY(bulletFireAnimation.getShape().getY() + this.movementSpeed * delta);
			damageAnimation.setY(damageAnimation.getShape().getY() + this.movementSpeed * delta);
		}

	}
	
	/**
	 * aligns Enemy automatically with player
	 */
	// TODO check if there's a wall between player and enemy
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
		} else if(turnDirection < 0) {
			enemyRotationAngle = this.direction - this.rotationSpeed * delta;
		} else {
			enemyRotationAngle = targetRotationAngle;
		}
		
		
		this.setDirection(enemyRotationAngle);
		damageAnimation.setDirection(enemyRotationAngle);
		equippedWeapon.getBulletFire().setDirection(enemyRotationAngle);
	}
	
	protected int turnLeftOrRight(float currentDirection, float targetDirection) {
//		Quelle: ChatGPT
		
		float angleDifference = targetDirection - currentDirection;
		angleDifference = (angleDifference + 180) % 360 - 180;
		if (angleDifference > 0) {
			return 1; // Rotate right is shorter
			} else if (angleDifference < 0) {
				return -1;  // Rotate left is shorter
				} else {
					return 0;  // Angles are the same
					}
		}
	
	public void reload() {
		short requiredBullets = (short) (this.equippedWeapon.getMagazineSize() - this.equippedWeapon.getBullets());
		this.equippedWeapon.reload((short) 15);
	}
	
}
