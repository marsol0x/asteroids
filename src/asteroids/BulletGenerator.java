package asteroids;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.Vector;

public class BulletGenerator {
    private static BulletGenerator instance;

    private Vector<Bullet> bullets;

    private BulletGenerator() {
        bullets = new Vector<Bullet>();
    }

    public static final BulletGenerator getInstance() {
        if (instance == null) {
            instance = new BulletGenerator();
        }
        return instance;
    }

    public final Vector<Bullet> getBullets() { return bullets; }

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

    public void shootBullet(double x, double y, double rot) {
        Vector2 velocity = new Vector2(0.0, -15.0);

        velocity.rotate(rot);

        bullets.add(new Bullet(x, y, velocity.x, velocity.y));
    }

    public void drawBullets(Graphics2D g) {
        for (Bullet b : bullets) { b.draw(g); }
    }

    class Bullet extends Entity {
        private static final int ticktoDead = 30;
        public boolean dead;
        private int tick;

        public Bullet(double x, double y, double vx, double vy) {
            velocity = new Vector2(vx, vy);
            position = new Vector2(x, y);
            dead = false;
            tick = ticktoDead;
        }

        public boolean collided(Entity e) {
            return e.getShape().getBounds().contains((int) position.x, (int) position.y);
        }

        public void kill() {
            dead = true;
        }

        public void tick() {
            tick--;
            if (tick == 0) {
                dead = true;
            }
        }

        public void draw(Graphics2D g) {
            g.setColor(Color.WHITE);
            g.drawLine((int) position.x, (int) position.y, (int) position.x, (int) position.y);
        }
    }
}
