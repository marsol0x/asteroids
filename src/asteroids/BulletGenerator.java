package asteroids;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.Vector;

public class BulletGenerator {
    private static BulletGenerator instance;

    private Vector<Bullet> bullets;

    private BulletGenerator() {
        bullets = new Vector<Bullet>();
    }

    public final BulletGenerator getInstance() {
        if (instance == null) {
            instance = new BulletGenerator();
        }
        return instance;
    }

    public void tick() {
        for (Bullet b : bullets) {
            b.move();
            b.tick();
        }
        cullBullets();
    }

    public void cullBullets() {
        Vector<Bullet> deadBullets = new Vector<Bullet>();
        for (Bullet b : bullets) {
            if (b.dead) { deadBullets.add(b); }
        }
        bullets.removeAll(deadBullets);
    }

    class Bullet extends Entity {
        private static final int ticktoDead = 120;
        public boolean dead;
        private int tick;

        public Bullet(double x, double y, double vx, double vy) {
            velocity = new Vector2(vx, vy);
            position = new Vector2(x, y);
            dead = false;
            tick = ticktoDead;
        }

        public void tick() {
            tick--;
            if (tick == 0) {
                dead = true;
            }
        }

        public void draw(Graphics2D g) {
            // TODO: Draw
        }

        public Shape getShape() { return null; }
        public boolean collided(Entity e) { return false; }
    }
}
