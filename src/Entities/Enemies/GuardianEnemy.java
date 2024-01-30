package Entities.Enemies;

import java.util.HashMap;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Controllers.PlayerController;
import Entities.Enemy;
import Entities.Weapon;
import Entities.Weapons.AssaultRifle;
import Entities.Weapons.MachinePistol;
import Entities.Weapons.Shotgun;
import Entities.Weapons.SniperRifle;

public class GuardianEnemy extends Enemy{
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

			//Chosse random weapon
			Random random = new Random();
			int randomInt = random.nextInt(4);
			switch (randomInt) {
				case 0:
					this.equippedWeapon = new AssaultRifle(this);
					break;
				case 1:
					this.equippedWeapon = new MachinePistol(this);
					break;
				case 2:
					this.equippedWeapon = new Shotgun(this);
					break;
				case 3:
					this.equippedWeapon = new SniperRifle(this);
					break;
				default:
					break;
			}

			this.movementSpeed = 0.1f;
			this.rotationSpeed = 0.1f;
			this.hitpoints = 75;
		}

		/**
		 * 
		 */
		@Override
		public void render(Graphics g) {
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
		    a.put(this.equippedWeapon.getAmmoType(), this.equippedWeapon.getMagazineSize());
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
