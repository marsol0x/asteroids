package asteroids;

import java.lang.Math;

public class Vector2 {
    public double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    public Vector2 add(Vector2 v) {
        x = x + v.x;
        y = y + v.y;

        return this;
    }

    public Vector2 sub(Vector2 v) {
        x = x - v.x;
        y = y - v.y;

        return this;
    }

    public Vector2 rotateRad(double rad) {
        double curX = x;
        double curY = y;

        x = (curX * Math.cos(rad)) - (curY * Math.sin(rad));
        y = (curX * Math.sin(rad)) + (curY * Math.cos(rad));

        return this;
    }

    public Vector2 rotate(double deg) {
        double rad = deg * (Math.PI / 180);
        return rotateRad(rad);
    }

    public double getAngleRad() {
        return Math.atan2(y, x);
    }

    public double dist(Vector2 other) {
        return Math.sqrt(
                Math.pow(Math.abs(this.x - other.x), 2) +
                Math.pow(Math.abs(this.y - other.y), 2)
               );
    }
}
