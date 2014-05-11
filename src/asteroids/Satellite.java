package asteroids;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.lang.Math;

public class Satellite extends Entity {
    private static final double ROTATE_VALUE = 2.0; // Slow rotate
    private static final int POLY_SIDES = 6; // Hexagon

    private int w, h, radius;
    private Polygon bounds;
    private boolean big;

    public Satellite(double x, double y, boolean big) {
        position = new Vector2(x, y);
        velocity = new Vector2(Math.random(), Math.random());
        facing = 0.0;
        this.big = big;
        this.w = 50;
        this.h = 50;

        if (!big) {
            this.w /= 2;
            this.h /= 2;
            velocity.x *= 1.5;
            velocity.y *= 1.5;
        }

        radius = w / 2;

        // Create a hexagon
        entityPolygon  = new Polygon();
        for (int i = 0; i < POLY_SIDES; i++) {
            entityPolygon.addPoint(
                    (int) (radius * Math.cos(i * Math.PI * 2 / POLY_SIDES)),
                    (int) (radius * Math.sin(i * Math.PI * 2 / POLY_SIDES))
            );
        }
    }

    public boolean collided(Entity e) {
        if (position.dist(e.position) >= radius * 2) { return false; }

        return getShape().getBounds().intersects(e.getShape().getBounds());
    }

    public boolean isBig() { return big; }
    public final Vector2 getPosition() { return position; }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        facing += ROTATE_VALUE;

        g.draw(getShape());
    }
}
