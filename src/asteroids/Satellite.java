package asteroids;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.lang.Math;

public class Satellite extends Entity {
    private static final double ROTATE_VALUE = 2.0; // Slow rotate
    private int w, h;

    private Rectangle satelliteRect;

    public Satellite(double x, double y) {
        position = new Vector2(x, y);
        prevPosition = new Vector2(x, y);
        velocity = new Vector2(Math.random(), Math.random());
        facing = new Vector2(1.0, 0.0);
        this.w = 50;
        this.h = 50;

        satelliteRect = new Rectangle((int) x, (int) y, w, w);
    }

    public boolean collided(Entity e) {
        return satelliteRect.intersects(e.getShape().getBounds2D());
    }

    public final Shape getShape() { return satelliteRect; }

    public void draw(Graphics2D g) {
        facing.rotate(ROTATE_VALUE);

        BasicStroke stroke = new BasicStroke();
        AffineTransform transform = g.getTransform();
        //satelliteRect.translate((int) (position.x - prevPosition.x), (int) (position.y - prevPosition.y));
        satelliteRect.setLocation((int) position.x, (int) position.y);
        prevPosition.set(position.x, position.y);
        g.rotate(facing.getAngleRad(), satelliteRect.getX() + (satelliteRect.getWidth() / 2), satelliteRect.getY() + (satelliteRect.getHeight() / 2));

        g.setColor(Color.WHITE);
        g.setStroke(stroke);
        g.draw(satelliteRect);
        g.setTransform(transform);
    }
}
