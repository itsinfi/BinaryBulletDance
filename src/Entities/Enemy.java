package Entities;

import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import Entities.Weapon;
import Controllers.PlayerController;

import java.lang.Math;

public abstract class Enemy extends LivingEntity{

	public Enemy(String spriteAsset, float centerX, float centerY, float direction) throws SlickException {
		super(spriteAsset, centerX, centerY, direction);
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
		
//		deprecated
//		float distanceX = enemyX-playerX;
//		float distanceY = enemyY-playerY;
//		
//		System.out.println(distanceX);
//		System.out.println(distanceY);
		
//		this.setX(this.getShape().getX() - 1);
		
//		if (distanceX > 100) {
//			this.setX(this.getShape().getX() - this.movementSpeed * delta);
////			this.setX(enemyX);
//			
//		} else if(distanceX < 100) {
//			this.setX(this.getShape().getX() + this.movementSpeed * delta);
////			this.setX(enemyX);
//		} else if (distanceX == 100) {
////			this.setX(enemyX);
//		}
//		
//		if (distanceY > 100) {
//				this.setY(enemyY - this.movementSpeed * delta);
////				this.setY(enemyY + this.movementSpeed * delta);
//		}
		
		
//		previous attempt
		float distanceX = Math.abs(enemyX-playerX);
		float distanceY = Math.abs(enemyY-playerY);
		
		float maxDistance = 150;
		float minDistance = 75;
		
		if (distanceX > maxDistance) {
			if (enemyX > playerX) {
				this.setX(this.getShape().getX() - this.movementSpeed * delta);
			} else if (enemyX < playerX) {
				this.setX(this.getShape().getX() + this.movementSpeed * delta);
			}
			
		}
		if (distanceY > maxDistance) {
			if (enemyY > playerY) {
				this.setY(this.getShape().getY() - this.movementSpeed * delta);
			} else if (enemyY < playerY) {
				this.setY(this.getShape().getY() + this.movementSpeed * delta);
			}
			
		}
		
		if (distanceX < minDistance) {
			if (enemyX > playerX) {
				this.setX(this.getShape().getX() + this.movementSpeed * delta);
			} else if (enemyX < playerX) {
				this.setX(this.getShape().getX() - this.movementSpeed * delta);
			}
			
		}
		if (distanceY < minDistance) {
			if (enemyY > playerY) {
				this.setY(this.getShape().getY() + this.movementSpeed * delta);
			} else if (enemyY < playerY) {
				this.setY(this.getShape().getY() - this.movementSpeed * delta);
			}
			
		}
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
