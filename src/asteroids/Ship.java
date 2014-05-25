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
    private ParticleGenerator pGenerator;
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
        pGenerator = ParticleGenerator.getInstance();

        entityPolygon = new Polygon(new int[]{0, -w / 2, w / 2}, new int[]{-h / 2, h / 2, h / 2}, 3);
        thrusterPolygon = new Polygon(new int[]{0, w / 4, w / 2}, new int[]{0, 8, 0}, 3);
    }

    private void thrust() {
        Vector2 acceleration = new Vector2(0.0, -1.1);
        acceleration.rotate(facing);
        velocity.add(acceleration);
        thrustersOn = true;
    }

    private void decelerate() {
        if (!thrustersOn) {
            velocity.x *= 0.99;
            velocity.y *= 0.99;
        }
    }

    private void shoot() {
        bGenerator.shootBullet(position.x, position.y, facing);
    }

    public void kill() {
        dead = true;
        // Explode!
        ParticleGenerator.getInstance().generateExplosion(this);
    }

    public boolean isDead() { return dead; }

    public final Vector2 getPosition() { return position; }

    public void move() {
        if (!dead) {
            super.move();
            decelerate();

            if (thrustersOn) {
                // Generate a particle effect on the thrusters
                Vector2 pVelocity = velocity.copy();
                Vector2 acceleration = new Vector2(0.0, -1.1);
                acceleration.rotate(facing);
                acceleration.rotate(180.0); // This needs to come out of the back of the ship
                pVelocity.add(acceleration).add(acceleration); // Applied twice to cancel out the forward acceleration

                for (int i = 0; i < 32; i++) {
                    double vx = pVelocity.x + (Math.random() * (0.5) + 0.1) - 0.25;
                    double vy = pVelocity.y + (Math.random() * (0.5) + 0.1) - 0.25;
                    pGenerator.generateParticle(position.copy(), new Vector2(vx, vy));
                }
            }
        }
    }

    public void draw(Graphics2D g) {
        if (dead) return; // Don't draw if dead

        g.setColor(Color.BLACK);
        g.fill(getShape());
        g.setColor(Color.WHITE);
        g.draw(getShape());

        if (thrustersOn) {
            // Add a little flicker
            int xp, yp;
            xp = thrusterPolygon.xpoints[1];
            yp = thrusterPolygon.ypoints[1];
            thrusterPolygon.xpoints[1] = (xp - 2) + (int) (Math.random() * 4 + 1);
            thrusterPolygon.ypoints[1] = (yp - 2) + (int) (Math.random() * 4 + 1);

            GeneralPath p = new GeneralPath(thrusterPolygon);
            AffineTransform a = new AffineTransform();
            a.translate(position.x, position.y);
            a.rotate(Math.toRadians(facing));
            a.translate(-w / 4, h / 2);
            g.draw(p.createTransformedShape(a));

            // Reset thruster tip position
            thrusterPolygon.xpoints[1] = xp;
            thrusterPolygon.ypoints[1] = yp;
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
