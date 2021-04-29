package items;

import gameservice.Constants;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Circle extends Targets {
    public Circle(int hitPoints, Location location, float radius) {
        super(hitPoints);
        this.radius = radius;
        this.getLocation().set(location);
    }

    @Override
    public boolean interactBalls(Ball ball) {//TODO
        if (getLocation().distance(ball.getLocation())>= radius + ball.radius) return false;
        float cA=this.getLocation().connectAngle(ball.getLocation()),dA=ball.getDirectionAngle();
        float X=this.getLocation().getX(),Y=this.getLocation().getY();
        float d= (this.getLocation().distance(ball.getLocation())*Math.abs((float) Math.sin(ball.getDirectionAngle()-cA)));//d: Circle与球原始运动方向之距离
        //求Circle位置对球原始运动方向的垂足为X,Y
        float dX= (float) (d*Math.cos(dA+ Constants.PI/2.0f));
        float dY= (float) (d*Math.sin(dA+Constants.PI/2.0f));
        if(dX*(ball.getLocation().getX()-X)+dY*(ball.getLocation().getY()-Y)>0) {
            X += dX;
            Y+=dY;
        }else {
            X -= dX;
            Y -= dY;
        }
        //求碰撞时小球位置
        float l= (float) Math.sqrt(Math.pow((ball.radius+this.radius),2)-Math.pow(d,2));//l: 垂足与碰撞时小球位置的距离
        X+=-Math.cos(dA)*l;
        Y+=-Math.sin(dA)*l;
        Location collideLocation=new Location(X,Y);
        ball.reflect(this.getLocation().connectAngle(collideLocation),collideLocation);
        hit();
        return true;
    }

    @Override
    public void paintImage(Graphics2D gParent) {
        final float lineWidthDiv2= 3.0F;
        BufferedImage image=new BufferedImage((int)(2.0f*radius),(int)(2.0f*radius),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g= image.createGraphics();
        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(lineWidthDiv2));
        g.draw(new Ellipse2D.Float(lineWidthDiv2,lineWidthDiv2,(radius-lineWidthDiv2)*2.0f,(radius-lineWidthDiv2)*2.0f));
        //画出hitPoint
        g.setColor(Color.BLACK);
        String text=String.valueOf(this.getHitPoints());
        Rectangle2D textBounds2D=g.getFont().getStringBounds(text,null);
        g.drawString(text,(float) (radius-textBounds2D.getWidth()/2.0f),(float)( radius-textBounds2D.getHeight()/2.0f));
        gParent.drawImage(image,null,(int) (getLocation().getX() - radius), (int) (getLocation().getY() - radius));
    }
}
