package asteroids;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.lang.Math;
import java.util.Vector;

public class ParticleGenerator {
    private static ParticleGenerator instance;
    private static final int NUM_PARTS_EXPLOSION = 128; // Number of particles in an explosion

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

    public void reset() {
        particles.clear();
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

    public void generateParticle(Vector2 position, Vector2 velocity) {
        particles.add(new Particle(position, velocity));
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
            particles.add(new Particle(source.position.copy(), velocity.copy()));
        }
    }

    class Particle extends Entity {
        private static final int tickToDeadMin = 45;
        private static final int tickToDeadMax = 60;
        private boolean dead;
        private int tick;
        private int tickToDead;

        public Particle(Vector2 position, Vector2 velocity) {
            this.velocity = velocity;
            this.position = position;
            dead = false;
            tickToDead = tickToDeadMin + (int) (Math.random() * (tickToDeadMax - tickToDeadMin));
            tick = tickToDead;
        }

        public boolean isDead() { return dead; }
        public void kill() { dead = true; }

        public void tick() {
            tick--;
            if (tick == 0) {
                kill();
            }
            // Decelerate
            velocity.x *= 0.99;
            velocity.y *= 0.99;
        }

        public void draw(Graphics2D g) {
            g.setColor(new Color(255 - (tickToDead - tick), Math.random() > 0.5 ? 0 : 165, 0));
            g.drawLine((int)position.x, (int)position.y, (int)position.x, (int)position.y);
        }

        public boolean collided(Entity e) { return false; }
    }
}
