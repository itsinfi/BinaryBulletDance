package Entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

//TODO: removen und Logik durch Weapons einbauen
/**
 * @author Sascha Angermann
 */
@Deprecated
public class Bullet {

    private float xPos;
    private float yPos;
    private float rotationX;
    private float rotationY;
    private float rotationAngle;
    boolean isShooting = false;
    private Vector2f bulletDirection;

    public Bullet(Vector2f bulletDirection, float bulletX, float bulletY) {
        this.xPos = bulletX;
        this.yPos = bulletY;
        this.bulletDirection = bulletDirection;
        isShooting = true;
        this.rotationX = this.xPos;
        this.rotationY = this.yPos;
        this.rotationAngle = (float) Math.toDegrees(Math.atan2(bulletDirection.getY(), bulletDirection.getX()));
        // double angleRadians = bulletDirection.getTheta();
        // if (bulletDirection.getY() < 0) {
        //     angleRadians = 2 * Math.PI - angleRadians;
        // }
        // this.rotationAngle = (float) Math.toDegrees(angleRadians);
    }

    public void updateBullet(GameContainer container, float bulletSpeed, int delta) {

        if (isShooting) {
            xPos += bulletDirection.getX() * bulletSpeed * delta;
            yPos += bulletDirection.getY() * bulletSpeed * delta;

            // Check for bullet collision with the screen boundaries
            if (xPos < 0 || xPos > container.getWidth() || yPos < 0 || yPos > container.getHeight()) {
                isShooting = false;
            }
        }

    }

    public boolean getIsShooting() {
        return this.isShooting;
    }

    public Vector2f getBulletDirection() {
        return this.bulletDirection;
    }

    public float getRotationX() {
        return this.rotationX;
    }

    public float getRotationY() {
        return this.rotationY;
    }

    public float getRotationAngle() {
        return this.rotationAngle;
    }

    public void drawBullet(Graphics g) throws SlickException {
        Image bulletAsset = new Image("assets/bullet.png");
        bulletAsset.rotate(this.getRotationAngle());
        bulletAsset.draw(xPos, yPos);

        // g.pushTransform();
        // float rotationAngle = this.getRotationAngle();
        // System.out.println(rotationAngle);
        // g.rotate(this.getRotationX(), this.getRotationY(), (rotationAngle != 0) ? rotationAngle / 360.0f : rotationAngle);
        // Color color = new Color(255, 255, 0, 200);
        // g.setColor(color);
        // g.fillRoundRect(xPos, yPos, 15, 5, 5);
        // g.fillOval(xPos, yPos, 10, 10);
        // g.popTransform();
    }

}
