package asteroids;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.lang.Math;

public class Satellite extends Entity {
    private static final double ROTATE_VALUE = 2.0; // Slow rotate

    private int radius;
    private Polygon bounds;
    private int sides;

    public Satellite(double x, double y, int sides) {
        position = new Vector2(x, y);
        velocity = new Vector2(Math.random(), Math.random());
        facing = 0.0;
        this.sides = sides;
        radius = 50 * sides / 12;

        // Create a hexagon
        entityPolygon  = new Polygon();
        for (int i = 0; i < sides; i++) {
            entityPolygon.addPoint(
                    (int) (radius * Math.cos(i * Math.PI * 2 / sides)),
                    (int) (radius * Math.sin(i * Math.PI * 2 / sides))
            );
        }
    }

    public Satellite(Satellite source, BulletGenerator.Bullet killer) {
        this(source.position.x, source.position.y, source.getSides() - 1);
        velocity = source.velocity.copy();

        Vector2 v = new Vector2(velocity.x, velocity.y);
        v.rotateRad(killer.velocity.getAngleRad() + Math.random());
        velocity.add(v);
    }

    public boolean collided(Entity e) {
        if (position.dist(e.position) >= radius * 2) { return false; }

        return getShape().getBounds().intersects(e.getShape().getBounds());
    }

    public int getSides() { return sides; }
    public final Vector2 getPosition() { return position; }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        facing += ROTATE_VALUE;

        g.draw(getShape());
    }
}
