package ui;

import gameservice.Constants;
import gameservice.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 暂停界面类，切换至该界面需要更新scoreLabel
 */
public class PauseMenu extends Dialog {
    JLabel lblScore;

    PauseMenu() {

        lblBackGround.setIcon(new ImageIcon(Constants.imagePauseMenuBGFileName));
        JButton btnContinue = new JButton();
        lblScore = new JLabel(String.valueOf(Game.getScore()), JLabel.CENTER);
        lblScore.setFont(new Font("Arial", Font.BOLD, 40));
        lblScore.setBounds(93, 196, 134, 81);
        lblScore.setForeground(Color.white);
        btnContinue.setBounds(86, 377, 148, 54);
        btnContinue.setContentAreaFilled(false);
        add(btnContinue, 0);//ZOrder按加入顺序从0到大，绘图时按从大到小绘制，故先加入的在上面
        add(lblScore, 0);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rank.isNewRank(Game.getScore());
                Frame.showMenu();
            }
        });
        btnContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.goOn();
                Frame.showGamePanel();
            }
        });
    }

    /**
     * 在切换至该页面时设置
     *
     * @param score 此时分数
     */
    void setPauseMenu(int score) {
        this.lblScore.setText(String.valueOf(score));
    }
}
