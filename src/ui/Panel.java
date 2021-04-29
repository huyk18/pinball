package ui;

import gameservice.Constants;
import items.Ball;
import items.Targets;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class Panel extends JPanel {
    Vector<Ball> balls;
    Vector<Targets> targets;

    public Panel(Vector<Ball> balls, Vector<Targets> targets) {
        this.balls = balls;
        this.targets = targets;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(drawBufferedImage(), 0, 0, this);
    }

    private BufferedImage drawBufferedImage() {
        BufferedImage image = new BufferedImage(Constants.panelWidth,Constants.panelHeight,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        ImageIcon imageBG = new ImageIcon(Constants.imageBGFileName);
        g.drawImage(imageBG.getImage(), 0, 0, this);

        if (!targets.isEmpty()) {
            for (Targets target : targets) {
                target.paintImage(g);
            }
        }

        if (!balls.isEmpty()) {
            for (Ball ball : balls) {
                ball.paintImage(g);
            }
        }
        return image;
    }
}
