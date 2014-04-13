package asteroids;

import java.awt.Graphics2D;

abstract public class Entity {
    Vector2 velocity;

    abstract public void rotate(double rads);
    abstract public void draw(Graphics2D g);
}
