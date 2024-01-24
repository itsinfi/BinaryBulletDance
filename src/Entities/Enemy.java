package Entities;

import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import Entities.Weapon;
import Controllers.PlayerController;

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
