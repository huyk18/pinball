package items;

import java.awt.*;

public class Location {
    private double x;
    private double y;

    public Location(double x, double y) {
        set(x, y);
    }

    public Location(Point p) {
        x = p.getX();
        y = p.getY();
    }

    public void set(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    public void set(Location location) {
        this.setX(location.getX());
        this.setY(location.getY());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double distance(Location l2){
        return Math.sqrt((this.x-l2.x)*(this.x-l2.x)+(this.y- l2.y)*(this.y- l2.y));
    }
    public double connectAngle(Location l2) {
        return Math.atan2(l2.getY() - this.getY(), l2.getX() - this.getX());
    }
}
