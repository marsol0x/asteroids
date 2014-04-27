package asteroids;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.lang.Math;

public class Ship extends Entity implements KeyListener {
    private static final double ROTATE_VALUE = 22.5; // ~ PI / 8
    private boolean dead;
    private boolean thrustersOn;
    private int w, h;

    private BulletGenerator bGenerator;
    private Polygon shipPolygon;
    private Polygon thrusterPolygon;

    public Ship(double x, double y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0.0, 0.0);
        facing = 0.0;
        thrustersOn = false;
        dead = false;
        this.w = 15;
        this.h = 25;

        bGenerator = BulletGenerator.getInstance();

        shipPolygon = new Polygon(new int[]{0, -w / 2, w / 2}, new int[]{-h / 2, h / 2, h / 2}, 3);
        thrusterPolygon = new Polygon(new int[]{0, w / 4, w / 2}, new int[]{0, 8, 0}, 3);
    }

    private void thrust() {
        Vector2 acceleration = new Vector2(0.0, -1.1);
        acceleration.rotate(facing);
        velocity.add(acceleration);
        thrustersOn = true;
    }

    private void shoot() {
        bGenerator.shootBullet(position.x, position.y, facing);
    }

    public void kill() {
        dead = true;
        // Explode!
        ParticleGenerator.getInstance().generateExplosion(position.x, position.y);
    }

    public boolean isDead() { return dead; }

    public final Vector2 getPosition() { return position; }
    public final Shape getShape() {
        Rectangle s = shipPolygon.getBounds();
        s.setLocation((int) position.x, (int) position.y);
        return s;
    }

    public void move() {
        if (!dead) { super.move(); }
    }

    public void draw(Graphics2D g) {
        if (dead) return; // Don't draw if dead

        g.setColor(Color.WHITE);

        GeneralPath p = new GeneralPath(shipPolygon);
        AffineTransform a = new AffineTransform();
        a.translate(position.x, position.y);
        a.rotate(Math.toRadians(facing));
        g.draw(p.createTransformedShape(a));

        if (thrustersOn) {
            p = new GeneralPath(thrusterPolygon);
            a = new AffineTransform();
            a.translate(position.x, position.y);
            a.rotate(Math.toRadians(facing));
            a.translate(-w / 4, h / 2);
            g.draw(p.createTransformedShape(a));
        }
    }

    public boolean collided(Entity e) { return false; }

    // Handle user input
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                facing -= ROTATE_VALUE;
                break;
            case KeyEvent.VK_RIGHT:
                facing += ROTATE_VALUE;
                break;
            case KeyEvent.VK_UP:
                thrust();
                break;
            case KeyEvent.VK_SPACE:
                shoot();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            thrustersOn = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
