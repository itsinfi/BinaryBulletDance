package Entities;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import Entities.Weapon;
import Controllers.PlayerController;

import java.lang.Math;
import java.util.Random;

public abstract class Enemy extends LivingEntity{
	
	float targetPosX;
	float targetPosY;

	public Enemy(String spriteAsset, float centerX, float centerY, float direction) throws SlickException {
		super(spriteAsset, centerX, centerY, direction);
		this.targetPosX = this.getShape().getX();
		this.targetPosY = this.getShape().getY();
		// TODO Auto-generated constructor stub
	}

    //TODO:

    

//	/**
//	 * 
//	 */
//    @Override
//    public void die() {
//        // TODO: Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented method 'die'");
//    }
	
	/**
	 * moves enemy dependant on player position and distance to player
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
//		float minDistance = 75;
		
		Random random = new Random();
		
		if (distanceX < maxDistance) {
			
			float min = playerX-range;
			float max = playerX+range;
			
			this.targetPosX =  ThreadLocalRandom.current().nextFloat(min, max);
	//		System.out.println("TargetPosX: " + this.targetPosX);
			
			System.out.println("playerx min range: " + min);
			System.out.println("playerx max range: " + max);
			System.out.println("player X: " + playerX);
			System.out.println("TargetPos X: "+this.targetPosX);
			
		}
		
		if (distanceY < maxDistance) {
			
			float min = playerY-range;
			float max = playerY+range;
			
			this.targetPosY = ThreadLocalRandom.current().nextFloat(min, max);
	//		System.out.println("TargetPosY: " + this.targetPosY);
			System.out.println("playery min range: " + min);
			System.out.println("playery max range: " + max);
			System.out.println("Player Y: " + playerY);
			System.out.println("TargetPos Y: "+this.targetPosY);
			
//			float range = ThreadLocalRandom.current().nextFloat(min, max)
		
		}

		if (enemyX > targetPosX) {
			this.setX(this.getShape().getX() - this.movementSpeed * delta);
		} else if (enemyX < targetPosX) {
			this.setX(this.getShape().getX() + this.movementSpeed * delta);
		}

		if (enemyY > targetPosY) {
			this.setY(this.getShape().getY() - this.movementSpeed * delta);
		} else if (enemyY < targetPosY) {
			this.setY(this.getShape().getY() + this.movementSpeed * delta);
		}

//		float distanceX = Math.abs(enemyX-playerX);
//		float distanceY = Math.abs(enemyY-playerY);
//		float maxDistance = 150;
//		float minDistance = 75;
//		if (distanceX > maxDistance) {
//			if (enemyX > playerX) {
//				this.setX(this.getShape().getX() - this.movementSpeed * delta);
//			} else if (enemyX < playerX) {
//				this.setX(this.getShape().getX() + this.movementSpeed * delta);
//			}
//			
//		}
//		if (distanceY > maxDistance) {
//			if (enemyY > playerY) {
//				this.setY(this.getShape().getY() - this.movementSpeed * delta);
//			} else if (enemyY < playerY) {
//				this.setY(this.getShape().getY() + this.movementSpeed * delta);
//			}
//			
//		}
//		
//		if (distanceX < minDistance) {
//			if (enemyX > playerX) {
//				this.setX(this.getShape().getX() + this.movementSpeed * delta);
//			} else if (enemyX < playerX) {
//				this.setX(this.getShape().getX() - this.movementSpeed * delta);
//			}
//			
//		}
//		if (distanceY < minDistance) {
//			if (enemyY > playerY) {
//				this.setY(this.getShape().getY() + this.movementSpeed * delta);
//			} else if (enemyY < playerY) {
//				this.setY(this.getShape().getY() - this.movementSpeed * delta);
//			}
//			
//		}
	}
	
	/**
	 * aligns Enemy automatically with player
	 */
	// TODO check if there's a wall between player and enemy
	public void alignWithPlayer() {
		float enemyX = this.getShape().getCenterX();
		float enemyY = this.getShape().getCenterY();
		
		float playerX = PlayerController.getPlayer().getShape().getCenterX();
		float playerY = PlayerController.getPlayer().getShape().getCenterY();
		
		Vector2f enemyDirection = new Vector2f(playerX - enemyX, playerY - enemyY).normalise();
		float enemyRotationAngle = (float) Math.toDegrees(Math.atan2(enemyDirection.getY(), enemyDirection.getX()));
		this.setDirection(enemyRotationAngle);

	}
    
}
