package Controllers;

import java.util.HashSet;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import Entities.Bullet;
import Entities.LivingEntity;
import Weapons.Weapon;

/**
 * Diese Klasse verwaltet alle Waffen im Game.
 * 
 * @author Sascha Angermann
 */
//TODO: ANNOTATIONEN!!!
public class WeaponController {
    

    //Attribute

    private HashSet<LivingEntity> livingEntities;
    private HashSet<Bullet> bullets = new HashSet<Bullet>();//TODO: Zu Weapon Class ersetzen
    private HashSet<Weapon> weapons = new HashSet<Weapon>();//TODO:


    //Konstruktoren

    //TODO: Konstruktor updaten + Annotationen hinzufügen
    public WeaponController(HashSet<LivingEntity> livingEntities) {
        this.livingEntities = livingEntities;
        this.bullets = new HashSet<Bullet>();
    }


    //Methoden
    
    //TODO: Methoden updaten nach UML + Annotationen hinzufügen
    public void update(Input input, float bulletSpeed, int delta, GameContainer container, LivingEntity livingEntity) {
        // Update bullet position and check for collisions
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            if (bullet.getIsShooting()) {
                bullet.updateBullet(container, bulletSpeed, delta);
            } else {
                it.remove();//ALTER LIVE SAVER SUPER WICHTIG MERKEN YO
            }
        }
    }

    public void render(Graphics g) {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            try {
                it.next().drawBullet(g);
            } catch (SlickException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void shoot(Input input, LivingEntity livingEntity) {
        // livingEntity.getEquipped().attack(0, 0, 0);

        float mouseX = input.getMouseX();
        float mouseY = input.getMouseY();
        float bulletX = livingEntity.getShape().getCenterX() + 25;
        float bulletY = livingEntity.getShape().getCenterY() + 40;
        Vector2f bulletDirection = new Vector2f(mouseX - bulletX, mouseY - bulletY).normalise();
        Bullet bullet = new Bullet(bulletDirection, bulletX, bulletY, livingEntity);
        bullets.add(bullet);
    }

}
