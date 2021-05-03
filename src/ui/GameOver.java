package ui;

import gameservice.Constants;
import gameservice.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOver extends Dialog {
    JLabel lblScore;

    GameOver() {
        lblBackGround.setIcon(new ImageIcon(Constants.imageGameOverBGFileName));
        lblScore = new JLabel(String.valueOf(Game.getScore()), JLabel.CENTER);
        lblScore.setFont(new Font("Arial", Font.BOLD, 40));
        lblScore.setBounds(93, 150, 134, 81);
        lblScore.setForeground(Color.white);
        //ZOrder按加入顺序从0到大，绘图时按从大到小绘制，故先加入的在上面
        add(lblScore, 0);

        //Game Over标签
        JLabel lblGameOver = new JLabel("Game Over", JLabel.CENTER);
        lblGameOver.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 42));
        lblGameOver.setBounds(43, 245, 234, 81);
        lblGameOver.setForeground(new Color(249, 249, 192));
        //ZOrder按加入顺序从0到大，绘图时按从大到小绘制，故先加入的在上面
        add(lblGameOver, 0);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame.showMenu();
            }
        });
    }

    /**
     * 在切换至该页面时设置,更新该页面
     *
     * @param score 此时分数
     */
    void setGameOver(int score) {
        this.lblScore.setText(String.valueOf(score));
        Rank.isNewRank(score);
    }
}
