package asteroids;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.lang.Math;
import java.util.Vector;

public class ParticleGenerator {
    private static ParticleGenerator instance;
    private static final int NUM_PARTS_EXPLOSION = 32; // Number of particles in an explosion

    private Vector<Particle> particles;

    private ParticleGenerator() {
        particles = new Vector<Particle>();
    }

    public static final ParticleGenerator getInstance() {
        if (instance == null) {
            instance = new ParticleGenerator();
        }

        return instance;
    }

    public void tick() {
        for (Particle p : particles) {
            p.move();
            p.tick();
        }
        cullParticles();
    }

    public void cullParticles() {
        Vector<Particle> deadParticles = new Vector<Particle>();
        for (Particle p : particles) {
            if (p.isDead()) { deadParticles.add(p); }
        }

        particles.removeAll(deadParticles);
    }

    public void drawParticles(Graphics2D g) {
        for (Particle p : particles) {
            p.draw(g);
        }
    }

    public void generateExplosion(Entity source) {
        Vector2 acceleration = new Vector2(0.0, 1.0);
        acceleration.rotate(-source.facing);
        double rot = 45.0 / NUM_PARTS_EXPLOSION;
        for (int i = 0; i < NUM_PARTS_EXPLOSION; i++) {
            Vector2 velocity = source.velocity.copy();
            acceleration.rotate(rot);
            velocity.add(acceleration);
            velocity.x *= Math.random();
            velocity.y *= Math.random();
            particles.add(new Particle(source.position.x, source.position.y, velocity.x, velocity.y));
        }
    }

    class Particle extends Entity {
        private static final int tickToDead = 60;
        private boolean dead;
        private int tick;

        public Particle(double x, double y, double vx, double vy) {
            velocity = new Vector2(vx, vy);
            position = new Vector2(x, y);
            dead = false;
            tick = tickToDead;
        }

        public boolean isDead() { return dead; }
        public void kill() { dead = true; }

        public void tick() {
            tick--;
            if (tick == 0) {
                kill();
            }
        }

        public void draw(Graphics2D g) {
            g.setColor(new Color(255 - (tickToDead - tick), Math.random() > 0.5 ? 0 : 165, 0));
            g.drawLine((int)position.x, (int)position.y, (int)position.x, (int)position.y);
        }

        public boolean collided(Entity e) { return false; }
    }
}
