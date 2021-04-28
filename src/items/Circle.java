package items;

import java.awt.*;

public class Circle extends Targets {
    public Circle(int hitPoints, Location location, double radius) {
        super(hitPoints);
        this.radius = radius;
        this.getLocation().set(location);
    }

    @Override
    public boolean interactBalls(Ball ball) {//TODO
        if (getLocation().distance(ball.getLocation())>= radius + ball.radius) return false;
        double cA=this.getLocation().connectAngle(ball.getLocation()),dA=ball.getDirectionAngle();
        double X=this.getLocation().getX(),Y=this.getLocation().getY();
        double d=this.getLocation().distance(ball.getLocation())*Math.abs(Math.sin(ball.getDirectionAngle()-cA));//d: Circle与球原始运动方向之距离
        //求Circle位置对球原始运动方向的垂足为X,Y
        double dX=d*Math.cos(dA+Math.PI/2.0);
        double dY=d*Math.sin(dA+Math.PI/2.0);
        if(dX*(ball.getLocation().getX()-X)+dY*(ball.getLocation().getY()-Y)>0) {
            X += dX;
            Y+=dY;
        }else {
            X -= dX;
            Y -= dY;
        }
        //求碰撞时小球位置
        double l=Math.sqrt(Math.pow((ball.radius+this.radius),2)-Math.pow(d,2));//l: 垂足与碰撞时小球位置的距离
        X+=-Math.cos(dA)*l;
        Y+=-Math.sin(dA)*l;
        Location collideLocation=new Location(X,Y);
        ball.reflect(this.getLocation().connectAngle(collideLocation),collideLocation);
        hit();
        return true;
    }

    @Override
    public void paintImage(Graphics g) {
        //TODO
        g.drawOval((int) (getLocation().getX() - radius), (int) (getLocation().getY() - radius), (int) (2.0 * radius), (int) (2.0 * radius));
    }
}
