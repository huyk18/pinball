package ui;

import gameservice.Audio;
import gameservice.Constants;
import gameservice.Game;
import items.Ball;
import items.Location;
import items.Targets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Frame extends JFrame {
    Vector<Ball> balls;
    Vector<Targets> targets;

    public Frame() {

        balls = new Vector<Ball>();
        targets = new Vector<Targets>();
        Game game = new Game(Constants.panelWidth, Constants.panelHeight, balls, targets);

        //设置窗体
        setTitle("弹一弹");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Panel panel = new Panel(balls, targets);
        setContentPane(panel);
        panel.setPreferredSize(new Dimension(Constants.panelWidth, Constants.panelHeight));
        pack();
        setResizable(false);
        Audio.BGM.play();//播放BGM

        setVisible(true);

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                game.run();
                panel.repaint();
            }
        };
        timer.schedule(timerTask, 0, 1000 / Constants.fps);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                game.setNextMouseLoc(new Location(e.getPoint()));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);//TODO
            }
        });
    }
}