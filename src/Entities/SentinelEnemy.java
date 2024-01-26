package Entities;

import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Controllers.EnemyController;
import Entities.Weapons.Pistol;

public class SentinelEnemy extends Enemy {
	
	// constructor
	public SentinelEnemy(String spriteAsset, float centerX, float centerY, float direction) throws SlickException {
		super(spriteAsset, centerX, centerY, direction);
		Weapon w = (Weapon) new Pistol((LivingEntity) this);
		this.setEquippedWeapon(w);
		this.movementSpeed = 0.17f;
		this.hitpoints = 100;
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
	
	// getters and setters
	public void setEquippedWeapon(Weapon w) {
		this.equippedWeapon = w;
	}

}
