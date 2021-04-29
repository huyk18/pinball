package items;

import gameservice.Constants;

import java.awt.*;

public class Rectangle extends Targets {
    /**
     * radius-外接圆半径
     * angle-边的角度 [0,pi/2)
     */
    float angle;

    public Rectangle(int hitPoints, Location location, float radius, float angle) {
        super(hitPoints);
        this.getLocation().set(location);
        this.radius = radius;
        this.angle = angle;
    }

    @Override
    public boolean interactBalls(Ball ball) {
        if (distanceWithBall(ball) > radius + ball.radius) return false;
        float connectAngle = this.getLocation().connectAngle(ball.getLocation());
        //目前小球最近的边，从angle对应的边按逆时针[0..4)，用于判断是否相撞
        int sideNo = (int) (Math.floor((connectAngle - angle - Constants.PI / 4.0f) / (Constants.PI / 2.0f))) % 4;
        float sideAngle=angle + sideNo * Constants.PI / 2.0f;
        float sideLen= (this.radius *  (float)Math.sqrt(2.0f));
        if ((distanceWithBall(ball) * Math.abs(Math.sin(connectAngle - sideAngle))) > (ball.radius +sideLen/2.0f ))
            return false;
        //下求碰撞位置
        float d=  (sideLen/2.0f-ball.getLocation().distance(this.getLocation())*(float)Math.sin(sideAngle-connectAngle));//此时小球对对应边的垂距
        float l=  ((d+ball.radius)/(float)Math.sin(ball.getDirectionAngle()-sideAngle));//此时小球与撞击点的距离
        float cX=ball.getLocation().getX()-l*(float)Math.cos(ball.getDirectionAngle()),
                cY=ball.getLocation().getY()-l*(float)Math.sin(ball.getDirectionAngle());//撞击位置
        ball.reflect(sideAngle + Constants.PI / 2.0f,new Location(cX,cY));
        hit();
        return true;
    }

    @Override
    public void paintImage(Graphics2D g) {
        //TODO
        //g.setStroke();
        g.drawOval((int) (getLocation().getX() - radius), (int) (getLocation().getY() - radius), (int) (2.0f * radius), (int) (2.0f * radius));

    }
}
