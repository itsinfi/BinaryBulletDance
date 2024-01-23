package Entities;

import java.util.HashMap;

import org.newdawn.slick.SlickException;

import Entities.Weapon;

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
    
}
