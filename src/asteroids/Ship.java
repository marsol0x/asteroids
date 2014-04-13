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

    public Ship(double x, double y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0.0, 0.0);
        facing = new Vector2(1.0, 0.0);
        this.w = 15;
        this.h = 25;
    }

    public void draw(Graphics2D g) {
        Polygon shipPolygon = new Polygon(getXPoints(), getYPoints(), 3);
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

    // Get X Points to draw the ship
    private int[] getXPoints() {
        int x = (int) position.x;
        return new int[]{x, x - (w / 2), x + (w / 2)};
    }

    // Get Y Points to draw the ship
    private int[] getYPoints() {
        int y = (int) position.y;
        return new int[]{y - (h / 2), y + (h / 2), y + (h / 2)};
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
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
