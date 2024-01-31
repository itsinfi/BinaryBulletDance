package Entities.Enemies;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Entities.Enemy;
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
		 * renders guadian
		 */
		@Override
		public void render(Graphics g) {
			this.equippedWeapon.render();
			float x = this.shape.getX();
			float y = this.shape.getY();
			this.sprite.setRotation(direction);
			this.sprite.draw(x, y);
		}
}
