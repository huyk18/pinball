package items;

import java.awt.*;

public class Location{
    private float x;
    private float y;

    public Location(float x, float y) {
        set(x, y);
    }

    public Location(Point p) {
        x = (float) p.getX();
        y = (float) p.getY();
    }

    public void set(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    public void set(Location location) {
        this.setX(location.getX());
        this.setY(location.getY());
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float distance(Location l2) {
        return (float) Math.sqrt((this.x - l2.x) * (this.x - l2.x) + (this.y - l2.y) * (this.y - l2.y));
    }

    public float connectAngle(Location l2) {
        return (float) Math.atan2(l2.getY() - this.getY(), l2.getX() - this.getX());
    }
}
