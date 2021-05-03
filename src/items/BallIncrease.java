package items;

import gameservice.Game;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static gameservice.Constants.lineWidthDiv2;

/**
 * 永久增加一个小球道具
 */
public class BallIncrease extends Circle {
    public BallIncrease(Location location, float radius){
        super(1,location,radius);
    }

    @Override
    public boolean hit() {
        Game.setBallNum(Game.getBallNum()+1);
        return super.hit();
    }

    @Override
    public void paintImage(Graphics2D gParent) {
        BufferedImage image = new BufferedImage((int) (2.0f * radius), (int) (2.0f * radius), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(lineWidthDiv2));
        g.draw(new Ellipse2D.Float(lineWidthDiv2, lineWidthDiv2, (radius - lineWidthDiv2) * 2.0f, (radius - lineWidthDiv2) * 2.0f));
        //画出hitPoint
        g.setColor(Color.white);
        g.setFont(new Font("Default", Font.BOLD, 20));
        Rectangle2D textBounds2D = g.getFont().getStringBounds("+", new FontRenderContext(null, true, true));
        g.drawString("+", (radius - (float) textBounds2D.getWidth() / 2.0f), (radius - (float) textBounds2D.getY() / 2f));
        gParent.drawImage(image, null, (int) (getLocation().getX() - radius), (int) (getLocation().getY() - radius));
    }
}
