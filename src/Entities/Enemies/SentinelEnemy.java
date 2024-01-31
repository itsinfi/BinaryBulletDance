package Entities.Enemies;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

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
		
		Shape[] shapes = {this.shape, this.equippedWeapon.getShape(), this.equippedWeapon.getBulletFire().getShape(), this.damageAnimation.getShape()};
		this.flyingAnimation = new FlyingAnimation(shapes);
	}

	/**
	 * renders sentinel
	 */
	@Override
	public void render(Graphics g) {
		float x = this.shape.getX();
		float y = this.shape.getY();
		this.sprite.setRotation(direction);
		this.sprite.draw(x, y);

		flyingAnimation.update();
		flyingAnimation.render(g);
	}

}
