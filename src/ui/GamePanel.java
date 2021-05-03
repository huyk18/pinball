package ui;

import gameservice.Constants;
import gameservice.Game;
import items.Ball;
import items.Location;
import items.Targets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public class GamePanel extends JPanel {
    Game game;

    public GamePanel(Game game) {
        this.game = game;
        addMouseListener(new MouseAdapter() {//判断小球发射和返回键
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //返回键判断
                int X = e.getX(), Y = e.getY();
                if (X > 276 && X < 311 && Y < 42 && Y > 11) {
                    Game.pause();
                    Frame.showPauseMenu(Game.getScore());
                }
                game.setNextMouseLoc(new Location(e.getPoint()));
            }

//            @Override
//            public void mouseMoved(MouseEvent e) {
//                super.mouseMoved(e);//TODO 瞄准器追踪鼠标
//            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(drawBufferedImage(), 0, 0, this);
    }

    private BufferedImage drawBufferedImage() {
        Constants.lock.lock();
        BufferedImage image = new BufferedImage(Constants.panelWidth, Constants.panelHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        ImageIcon imageBG = new ImageIcon(Constants.imageBGFileName);
        g.drawImage(imageBG.getImage(), 0, 0, this);

        List<Targets> targets = Game.getTargets();
        List<Ball> balls = Game.getBalls();
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
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString(String.valueOf(Game.getScore()), Constants.scoreTextX, Constants.scoreTextY);
        Constants.lock.unlock();
        return image;
    }
}
