package items;

import gameservice.Constants;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Triangle extends Targets {

    /**
     * radius-外接圆半径
     * angle-边的角度 [0,pi*2/3)
     */
    float angle;

    public Triangle(int hitPoints, Location location, float radius, float angle) {
        super(hitPoints);
        this.radius = radius;
        this.angle = angle;
        this.getLocation().set(location);
    }

    @Override
    public boolean interactBalls(Ball ball) {
        if (distanceWithBall(ball) > radius + ball.radius) return false;
        float connectAngle = this.getLocation().connectAngle(ball.getLocation());
        //目前小球最近的边，从angle对应的边按逆时针[0..3)，用于判断是否相撞
        int sideNo = ((int) (Math.floor((connectAngle - angle - Constants.PI / 6.0f) / (Constants.PI * 2.0f / 3.0f)))) % 3;
        float sideAngle = angle + sideNo * Constants.PI * 2.0f / 3.0f;
        if ((distanceWithBall(ball) * Math.abs(Math.sin(connectAngle - sideAngle))) > (ball.radius + this.radius / 2.0f))
            return false;
        //下求碰撞位置
        float d = radius / 2.0f - ball.getLocation().distance(this.getLocation()) * Math.abs((float) Math.sin(sideAngle - connectAngle));//此时小球对对应边的垂距
        float l = (d + ball.radius) / Math.abs((float) Math.sin(ball.getDirectionAngle() - sideAngle));//此时小球与撞击点的距离
        float cX = ball.getLocation().getX() - l * (float) Math.cos(ball.getDirectionAngle()),
                cY = ball.getLocation().getY() - l * (float) Math.sin(ball.getDirectionAngle());//撞击位置
        ball.reflect(sideAngle + Constants.PI / 2.0f, new Location(cX, cY));
        return true;
    }

    @Override
    public void paintImage(Graphics2D gParent) {
        BufferedImage image = new BufferedImage((int) (2.0f * radius), (int) (2.0f * radius), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //矩形画布旋转
        g.rotate(Math.PI+this.angle, (int) radius, (int) radius);
        //画出三角形
        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(Constants.lineWidthDiv2));
        float X1 = radius - radius * Constants.sqrt3 / 2.0f + Constants.sqrt3 * Constants.lineWidthDiv2,
                Y1 = radius / 2.0f + Constants.lineWidthDiv2,
                X2 = 2 * radius - X1,
                Y2 = Y1,
                X3 = radius,
                Y3 = 2 * radius - 2 * Constants.lineWidthDiv2;
        Path2D  triangle2D=new Path2D.Float();
        triangle2D.moveTo(X1,Y1);
        triangle2D.lineTo(X2,Y2);
        triangle2D.lineTo(X3,Y3);
        triangle2D.closePath();
        g.draw(triangle2D);
        //画布旋转回来
        g.rotate(-(Math.PI+this.angle),(int)radius,(int)radius);
        //画出hitPoint
        g.setColor(Color.BLACK);
        String text = String.valueOf(this.getHitPoints());
        g.setFont(new Font("Tekton Pro", Font.BOLD, 20));
        Rectangle2D textBounds2D = g.getFont().getStringBounds(text, new FontRenderContext(null, true, true));
        g.drawString(text, (radius - (float) textBounds2D.getWidth() / 2.0f), (radius/*+(float)textBounds2D.getHeight()/2.0f*/));

        gParent.drawImage(image, null, (int) (getLocation().getX() - radius), (int) (getLocation().getY() - radius));
    }
}
