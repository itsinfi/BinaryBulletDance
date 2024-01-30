package Entities.Enemies;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Entities.Enemy;
import Entities.LivingEntity;
import Entities.Weapon;
import Entities.Animations.FlyingAnimation;
import Entities.Weapons.Pistol;

public class SentinelEnemy extends Enemy {

	private FlyingAnimation flyingAnimation;
	
	// constructor
	/**
	 * creates a new Sentinel
	 * @param spriteAsset - where the sprite is located
	 * @param centerX - where the sentinel is spawned on the x-axis
	 * @param centerY - where the sentinel is spawned on the y-axis
	 * @param direction - starting direction where the sentinel is looking towards
	 * @throws SlickException
	 */
	public SentinelEnemy(String spriteAsset, float centerX, float centerY, float direction) throws SlickException {
		super(spriteAsset, centerX, centerY, direction);
		Weapon w = (Weapon) new Pistol((LivingEntity) this);
		this.setEquippedWeapon(w);
		this.movementSpeed = 0.15f;
		this.rotationSpeed = 0.15f;
		this.hitpoints = 25;
		
		this.flyingAnimation = new FlyingAnimation(shape);
	}

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
		flyingAnimation.update();
		flyingAnimation.render(g);		
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
