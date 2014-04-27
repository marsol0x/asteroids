package asteroids;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.lang.Math;

public class Satellite extends Entity {
    private static final double ROTATE_VALUE = 2.0; // Slow rotate
    private int w, h;

    private Polygon satelliteRect;

    public Satellite(double x, double y) {
        position = new Vector2(x, y);
        prevPosition = new Vector2(x, y);
        velocity = new Vector2(Math.random(), Math.random());
        facing = 0.0;
        this.w = 50;
        this.h = 50;

        satelliteRect = new Polygon(new int[]{0, w, w, 0}, new int[]{0, 0, h, h}, 4);
    }

    public boolean collided(Entity e) {
        Rectangle bounds = satelliteRect.getBounds();
        bounds.setLocation((int) position.x, (int) position.y);
        return bounds.intersects(e.getShape().getBounds2D());
    }

    public final Vector2 getPosition() { return position; }
    public final Shape getShape() { return satelliteRect; }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        facing += ROTATE_VALUE;

        GeneralPath p = new GeneralPath(satelliteRect);
        AffineTransform a = new AffineTransform();
        a.translate(position.x, position.y);
        a.rotate(Math.toRadians(facing));
        a.translate(-w / 2, -h / 2);
        g.draw(p.createTransformedShape(a));
    }
}
