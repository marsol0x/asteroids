package asteroids;

public class PhysVector {
    private double magnitude;
    private double direction; // In radians relative to object center

    public PhysVector(double magnitude, double direction) {
        this.magnitude = magnitude;
        this.direction = direction;
    }

    public double getMagnitude() { return magnitude; }
    public void setMagnitude(double magnitude) { this.magnitude = magnitude; }
    public double getDirection() { return direction; }
    public void setDirection(double direction) { this.direction = direction; }
}
