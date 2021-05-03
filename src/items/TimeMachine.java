package items;

import gameservice.Game;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static gameservice.Constants.lineWidthDiv2;

/**
 * 时光机道具：场上集合体后退两格，暂停生成两次
 */
public class TimeMachine extends Circle{
    public TimeMachine(Location location, float radius) {
        super(1, location, radius);
    }

    @Override
    public boolean hit() {
        Game.timeMachine();
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
        //画出增强符号
        g.setColor(Color.white);
        g.setFont(new Font("Default", Font.BOLD, 20));
        Rectangle2D textBounds2D = g.getFont().getStringBounds("T", new FontRenderContext(null, true, true));
        g.drawString("T", (radius - (float) textBounds2D.getWidth() / 2.0f), (radius - (float) textBounds2D.getY() / 2f));
        gParent.drawImage(image, null, (int) (getLocation().getX() - radius), (int) (getLocation().getY() - radius));
    }
}
