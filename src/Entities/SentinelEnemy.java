package Entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SentinelEnemy extends Enemy {
	
	// constructor
	public SentinelEnemy(String spriteAsset, float centerX, float centerY, float direction) throws SlickException {
		super(spriteAsset, centerX, centerY, direction);
		this.movementSpeed = 0.17f;
	}
	
//	public void update() {
//		alignWithPlayer();
//	}

	/**
	 * 
	 */
	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		float x = this.shape.getX();
		float y = this.shape.getY();
		this.sprite.setRotation(direction);
		this.sprite.draw(x, y);
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
		
	}

}
