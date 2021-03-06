package asteroids;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.lang.Math;

abstract public class Entity {
    private final double SPEED_LIMIT = 15.0;
    protected Vector2 velocity;
    protected Vector2 position;
    protected double facing;
    protected Polygon entityPolygon;

    abstract public void draw(Graphics2D g);
    abstract public boolean collided(Entity e);

    public final Shape getShape() {
        GeneralPath p = new GeneralPath(entityPolygon);
        AffineTransform a = new AffineTransform();
        a.translate(position.x, position.y);
        a.rotate(Math.toRadians(facing));

        return p.createTransformedShape(a);
    }

    public void move() {
        Dimension d = Asteroids.getInstance().getSize();

        if (velocity.x > SPEED_LIMIT || velocity.x < -SPEED_LIMIT) {
            velocity.setX(SPEED_LIMIT * Math.signum(velocity.x));
        }

        if (velocity.y > SPEED_LIMIT || velocity.y < -SPEED_LIMIT) {
            velocity.setY(SPEED_LIMIT * Math.signum(velocity.y));
        }

        position.add(velocity);

        if (position.x < 0) {
            position.setX(d.getWidth());
        }
        if (position.y < 0) {
            position.setY(d.getHeight());
        }
        if (position.x > d.getWidth()) {
            position.setX(position.x - d.getWidth());
        }
        if (position.y > d.getHeight()) {
            position.setY(position.y - d.getHeight());
        }
    }
}
