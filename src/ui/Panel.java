package ui;

import gameservice.Constants;
import items.Ball;
import items.Targets;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Panel extends JPanel {
    Image image = null;
    Vector<Ball> balls;
    Vector<Targets> targets;

    public Panel(Vector<Ball> balls, Vector<Targets> targets) {
        this.balls = balls;
        this.targets = targets;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawBufferedImage();
        g.drawImage(image, 0, 0, this);
    }

    private void drawBufferedImage() {
        image = createImage(Constants.panelWidth, Constants.panelHeight);
        Graphics g = image.getGraphics();

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
    }
}
