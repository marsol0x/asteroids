package asteroids;

import java.awt.Graphics2D;

abstract public class Entity {
    PhysVector velocity;

    abstract public void rotate(double rads);
    abstract public void draw(Graphics2D g);
}
