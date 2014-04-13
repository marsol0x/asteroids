package asteroids;

public class Vector2 {
    private double magnitude;
    private double direction; // In radians relative to object center

    public Vector2(double magnitude, double direction) {
        this.magnitude = magnitude;
        this.direction = direction;
    }

    public double getMagnitude() { return magnitude; }
    public void setMagnitude(double magnitude) { this.magnitude = magnitude; }
    public double getDirection() { return direction; }
    public void setDirection(double direction) { this.direction = direction; }
}
