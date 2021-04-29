package items;

import gameservice.Constants;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

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
        int sideNo = ((int) (Math.floor((connectAngle - angle - Constants.PI / 4.0f) / (Constants.PI / 2.0f)))) % 4;
        float sideAngle=angle + sideNo * Constants.PI / 2.0f;
        float sideLen= this.radius * Constants.sqrt2;
        if ((distanceWithBall(ball) * Math.abs(Math.sin(connectAngle - sideAngle))) > (ball.radius +sideLen/2.0f ))
            return false;
        //下求碰撞位置
        float d=  (sideLen/2.0f-ball.getLocation().distance(this.getLocation())*Math.abs((float)Math.sin(sideAngle-connectAngle)));//此时小球对对应边的垂距
        float l=  (d+ball.radius)/(float)Math.abs(Math.sin(ball.getDirectionAngle()-sideAngle));//此时小球与撞击点的距离
        float cX=ball.getLocation().getX()-l*(float)Math.cos(ball.getDirectionAngle()),
                cY=ball.getLocation().getY()-l*(float)Math.sin(ball.getDirectionAngle());//撞击位置
        ball.reflect(sideAngle + Constants.PI / 2.0f,new Location(cX,cY));
        return true;
    }

    @Override
    public void paintImage(Graphics2D gParent) {
        BufferedImage image=new BufferedImage((int)(2.0f*radius),(int)(2.0f*radius),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g= image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //矩形画布旋转
        g.rotate(this.angle,(int)radius,(int)radius);
        //画出矩形
        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(Constants.lineWidthDiv2));
        g.draw(new Rectangle2D.Float(radius-radius/Constants.sqrt2+ Constants.lineWidthDiv2,radius-radius/Constants.sqrt2+ Constants.lineWidthDiv2,(radius/Constants.sqrt2- Constants.lineWidthDiv2)*2.0f,(radius/Constants.sqrt2- Constants.lineWidthDiv2)*2.0f));
        //画布旋转回来
        g.rotate(-this.angle,(int)radius,(int)radius);
        //画出hitPoint
        g.setColor(Color.BLACK);
        String text=String.valueOf(this.getHitPoints());
        g.setFont(new Font("Tekton Pro",Font.BOLD,20));
        Rectangle2D textBounds2D=g.getFont().getStringBounds(text,new FontRenderContext(null,true,true));
        g.drawString(text,(radius-(float) textBounds2D.getWidth()/2.0f),( radius/*+(float)textBounds2D.getHeight()/2.0f*/));

        gParent.drawImage(image,null,(int) (getLocation().getX() - radius), (int) (getLocation().getY() - radius));
    }
}
