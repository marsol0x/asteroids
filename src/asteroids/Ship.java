package asteroids;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.lang.Math;

public class Ship extends Entity implements KeyListener {
    private static final double ROTATE_VALUE = Math.PI / 8.0;
    private int x, y, w, h;

    public Ship(int x, int y) {
        velocity = new Vector2(0.0, 0.0);
        this.x = x;
        this.y = y;
        this.w = 15;
        this.h = 25;
    }

    public void rotate(double rads) {
        double direction = velocity.getDirection();
        if (Math.abs(direction) + rads > Math.PI * 2) {
            direction = 0.0;
        }
        velocity.setDirection(direction + rads);
    }

    public void draw(Graphics2D g) {
        Polygon shipPolygon = new Polygon(getXPoints(), getYPoints(), 3);
        BasicStroke stroke = new BasicStroke();

        // Save current transform (rotation)
        AffineTransform transform = g.getTransform();
        // Rotate by ship rotation
        Rectangle2D bounds = shipPolygon.getBounds2D();
        g.rotate(velocity.getDirection(), bounds.getX() + (bounds.getWidth() / 2), bounds.getY() + (bounds.getHeight() / 2));

        // Draw the ship!
        g.setColor(Color.WHITE);
        g.setStroke(stroke);
        g.draw(shipPolygon);

        // Reset transform
        g.setTransform(transform);
    }

    // Get X Points to draw the ship
    private int[] getXPoints() {
        return new int[]{x, x - (w / 2), x + (w / 2)};
    }

    // Get Y Points to draw the ship
    private int[] getYPoints() {
        return new int[]{y - (h / 2), y + (h / 2), y + (h / 2)};
    }

    // Handle user input
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                rotate(-ROTATE_VALUE);
                break;
            case KeyEvent.VK_RIGHT:
                rotate(ROTATE_VALUE);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
