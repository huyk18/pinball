package items;

import java.awt.*;

public class Rectangle extends Targets {
    /**
     * radius-外接圆半径
     * angle-边的角度 [0,pi/2)
     */
    double angle;

    public Rectangle(int hitPoints, Location location, double radius, double angle) {
        super(hitPoints);
        this.getLocation().set(location);
        this.radius = radius;
        this.angle = angle;
    }

    @Override
    public boolean interactBalls(Ball ball) {
        if (distanceWithBall(ball) > radius + ball.radius) return false;
        double connectAngle = connectAngle(ball);
        //小球将接触的边，从angle对应的边按逆时针[0..4)
        int sideNo = (int) (Math.floor((connectAngle - angle - Math.PI / 4.0) / (Math.PI / 2.0))) % 4;
        if ((distanceWithBall(ball) * Math.abs(Math.sin(connectAngle - (angle + sideNo * Math.PI / 2.0)))) > (ball.radius + (this.radius * Math.sqrt(0.5))))
            return false;
        ball.reflect(angle + (sideNo + 1) * Math.PI / 2.0);
        hit();
        return true;
    }

    @Override
    public void paintImage(Graphics g) {
        //TODO
        g.drawOval((int) (getLocation().getX() - radius), (int) (getLocation().getY() - radius), (int) (2.0 * radius), (int) (2.0 * radius));

    }
}
