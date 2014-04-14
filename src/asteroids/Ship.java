package asteroids;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Ship extends Entity implements KeyListener {
    private static final double ROTATE_VALUE = 22.5; // ~ PI / 8
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 facing;
    private int w, h;

    private Polygon shipPolygon;

    public Ship(double x, double y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0.0, 0.0);
        facing = new Vector2(1.0, 0.0);
        this.w = 15;
        this.h = 25;

        shipPolygon = new Polygon(new int[]{0, -w / 2, w / 2}, new int[]{-h / 2, h / 2, h / 2}, 3);
        shipPolygon.translate((int) position.x, (int) position.y);
    }

    public void move() {
        position.add(velocity);
        shipPolygon.translate((int) velocity.x, (int) velocity.y);
    }

    private void thrust() {
        Vector2 acceleration = new Vector2(0.0, -1.0);
        acceleration.rotateRad(facing.getAngleRad());
        System.out.println("(" + acceleration.x + ", " + acceleration.y + ")");
        velocity.add(acceleration);
    }

    public void draw(Graphics2D g) {
        BasicStroke stroke = new BasicStroke();

        // Save current transform (rotation)
        AffineTransform transform = g.getTransform();
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
