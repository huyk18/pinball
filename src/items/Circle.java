package items;

import java.awt.*;

public class Circle extends Targets {
    public Circle(int hitPoints, Location location, double radius) {
        super(hitPoints);
        this.radius = radius;
        this.getLocation().set(location);
    }

    @Override
    public boolean interactBalls(Ball ball) {
        if (distanceWithBall(ball) > radius + ball.radius) return false;
        ball.reflect(connectAngle(ball));
        hit();
        return true;
    }

    @Override
    public void paintImage(Graphics g) {
        //TODO
        g.drawOval((int) (getLocation().getX() - radius), (int) (getLocation().getY() - radius), (int) (2.0 * radius), (int) (2.0 * radius));
    }
}
