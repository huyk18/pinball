package ui;

import gameservice.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Menu界面类
 * Menu界面类实现了主界面的按钮和背景
 */
public class Menu extends JPanel {
    JButton btnPlay, btnHelp, btnQuit, btnRank;
    JLabel lblBackGround;

    Menu() {
        super(null, true);
        btnPlay = new JButton();
        btnHelp = new JButton();
        btnQuit = new JButton();
        btnRank = new JButton();
        lblBackGround = new JLabel(new ImageIcon(Constants.imageMenuBGFileName));

        btnPlay.setBounds(86, 212, 148, 54);
        btnHelp.setBounds(86, 274, 148, 54);
        btnRank.setBounds(86, 336, 148, 54);
        btnQuit.setBounds(86, 398, 148, 54);
        btnPlay.setContentAreaFilled(false);
        btnHelp.setContentAreaFilled(false);
        btnRank.setContentAreaFilled(false);
        btnQuit.setContentAreaFilled(false);
        lblBackGround.setBounds(0, 0, 320, 568);

        add(btnPlay);
        add(btnHelp);
        add(btnQuit);
        add(btnRank);
        add(lblBackGround);

        btnPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame.showGamePanel();
            }
        });

        btnHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame.showHelp();
            }
        });

        btnRank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame.showRank();
            }
        });

        btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

}
