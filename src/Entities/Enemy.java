package Entities;

import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import Entities.Weapon;
import Controllers.PlayerController;

import java.lang.Math;
import java.util.random.*;

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
		
		float maxDistance = 10;
//		float minDistance = 75;
		
		if (distanceX < maxDistance) {
			if (Math.random()<0.5) {
			this.targetPosX = (float) (playerX + Math.random()*150)+150;
			} else {
				this.targetPosX = (float) (playerX - Math.random()*150-150);
			}
			
		}
		
		if (distanceY < maxDistance) {
			if (Math.random()<0.5) {
				this.targetPosY = (float) (playerY + Math.random()*150)+150;
				} else {
					this.targetPosY = (float) (playerY - Math.random()*150-150);
				}
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
