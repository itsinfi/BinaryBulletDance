package Entities.Enemies;

import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Controllers.PlayerController;
import Entities.Enemy;
import Entities.LivingEntity;
import Entities.Weapon;
import Entities.Weapons.AssaultRifle;

public class GuardianEnemy extends Enemy{
		private String ammoType;
		// constructor
		/**
		 * creates a new Guardian. Guardians are slower but stronger than sentinels
		 * @param spriteAsset - where the sprite is located
		 * @param centerX - where the guardian is spawned on the x-axis
		 * @param centerY - where the guardian is spawned on the y-axis
		 * @param direction - starting direction where the guardian is looking towards
		 * @throws SlickException
		 */
		public GuardianEnemy(String spriteAsset, float centerX, float centerY, float direction) throws SlickException {
			super(spriteAsset, centerX, centerY, direction);
			Weapon w = (Weapon) new AssaultRifle((LivingEntity) this);
			this.setEquippedWeapon(w);
			this.ammoType = "ASSAULT_RIFLE";
			this.movementSpeed = 0.1f;
			this.rotationSpeed = 0.1f;
			this.hitpoints = 75;
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
			this.equippedWeapon.render();
		}

		@Override
		public void die() {
			// replenish ammo for player when defeated
		    HashMap<String, Short> a = new HashMap<String, Short>();
		    a.put(this.ammoType, (short)(30));
			PlayerController.getPlayer().addAmmo(a);
			
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
