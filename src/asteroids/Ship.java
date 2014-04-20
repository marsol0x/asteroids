package asteroids;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Ship extends Entity implements KeyListener {
    private static final double ROTATE_VALUE = 22.5; // ~ PI / 8
    private boolean dead;
    private int w, h;

    private Polygon shipPolygon;

    public Ship(double x, double y) {
        position = new Vector2(x, y);
        prevPosition = new Vector2(x, y);
        velocity = new Vector2(0.0, 0.0);
        facing = new Vector2(1.0, 0.0);
        dead = false;
        this.w = 15;
        this.h = 25;

        shipPolygon = new Polygon(new int[]{0, -w / 2, w / 2}, new int[]{-h / 2, h / 2, h / 2}, 3);
        shipPolygon.translate((int) position.x, (int) position.y);
    }

    private void thrust() {
        Vector2 acceleration = new Vector2(0.0, -1.0);
        acceleration.rotateRad(facing.getAngleRad());
        velocity.add(acceleration);
    }

    public void kill() {
        dead = true;
        // Explode!
        ParticleGenerator.getInstance().generateExplosion(prevPosition.x, prevPosition.y);
    }

    public boolean isDead() { return dead; }

    public final Vector2 getPosition() { return position; }
    public final Shape getShape() { return shipPolygon; }

    public void move() {
        if (!dead) { super.move(); }
    }

    public void draw(Graphics2D g) {
        if (dead) return; // Don't draw if dead

        BasicStroke stroke = new BasicStroke();

        // Save current transform (rotation)
        AffineTransform transform = g.getTransform();

        // Position the ship
        shipPolygon.translate((int) (position.x - prevPosition.x), (int) (position.y - prevPosition.y));
        // Reset translation vector after positioning the ship
        prevPosition.set(position.x, position.y);

        // Rotate by ship rotation
        Rectangle2D bounds = shipPolygon.getBounds2D();
        g.rotate(facing.getAngleRad(), bounds.getX() + (bounds.getWidth() / 2), bounds.getY() + (bounds.getHeight() / 2));

        // Draw the ship!
        g.setColor(Color.WHITE);
        g.setStroke(stroke);
        g.draw(shipPolygon);

        // Reset transform
        g.setTransform(transform);
    }

    public boolean collided(Entity e) { return false; }

    // Handle user input
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                facing.rotate(-ROTATE_VALUE);
                break;
            case KeyEvent.VK_RIGHT:
                facing.rotate(ROTATE_VALUE);
                break;
            case KeyEvent.VK_UP:
                thrust();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
