package items;

import java.awt.*;

public class Triangle extends Targets {

    /**
     * radius-外接圆半径
     * angle-边的角度 [0,pi*2/3)
     */
    double angle;

    public Triangle(int hitPoints, Location location, double radius, double angle) {
        super(hitPoints);
        this.radius = radius;
        this.angle = angle;
        this.getLocation().set(location);
    }

    @Override
    public boolean interactBalls(Ball ball) {
        if (distanceWithBall(ball) > radius + ball.radius) return false;
        double connectAngle = this.getLocation().connectAngle(ball.getLocation());
        //小球将接触的边，从angle对应的边按逆时针[0..3)
        int sideNo = (int) (Math.floor((connectAngle - angle - Math.PI / 4.0) / (Math.PI * 2.0 / 3.0))) % 3;
        if ((distanceWithBall(ball) * Math.abs(Math.sin(connectAngle - (angle + sideNo * Math.PI * 2.0 / 3.0)))) > (ball.radius + (this.radius / 2.0)))
            return false;
        ball.reflect(angle + sideNo * Math.PI * 2.0 / 3.0 + Math.PI / 2.0);
        hit();
        return true;
    }

    @Override
    public void paintImage(Graphics g) {
        //TODO
        g.drawOval((int) (getLocation().getX() - radius), (int) (getLocation().getY() - radius), (int) (2.0 * radius), (int) (2.0 * radius));

    }
}
