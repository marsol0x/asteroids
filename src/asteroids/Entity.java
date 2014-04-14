package asteroids;

import java.awt.Graphics2D;

abstract public class Entity {
    protected Vector2 velocity;
    protected Vector2 position;
    protected Vector2 facing;

    abstract public void draw(Graphics2D g);
    abstract public void move();
}
