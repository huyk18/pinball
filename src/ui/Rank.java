package ui;

import gameservice.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Rank界面类
 * Rank界面类实现了对rank的排序、显示
 */
public class Rank extends Dialog {
    public static List<Integer> ranks = new ArrayList<>(){};//存储五个最高分，从小到大
    static JLabel lblRank;

    Rank() {
        JLabel lblTitle;
        lblTitle = new JLabel("RANK", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 54));
        lblTitle.setBounds(43, 80, 234, 81);
        lblTitle.setForeground(Color.white);
        add(lblTitle, 0);


        for (int i = 0; i < Constants.rankNum; i++) {
            ranks.add(0);
        }
        lblRank = new JLabel("", JLabel.LEFT);
        lblRank.setFont(new Font("Arial", Font.BOLD, 40));
        lblRank.setBounds(43, 150, 234, 230);
        lblRank.setForeground(Color.white);
        setRanks();
        add(lblRank, 0);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame.showMenu();
            }
        });
    }

    /**
     * 更新rank、更新rank文件
     * @param score 本次得分
     * @return 是否为新rank
     */
    public static boolean isNewRank(int score) {
        if (score < ranks.get(0)) return false;
        else {
            ranks.set(0, score);
            Collections.sort(ranks);
            Frame.writeRank();
            return true;
        }
    }

    /**
     * 设置Rank界面
     */
    public static void setRanks() {
        StringBuilder str = new StringBuilder("<html><body><div style=\"text-align:left\">");
        for (int i = 1; i <= Constants.rankNum; i++) {
            str.append(i).append("&nbsp &nbsp &nbsp &nbsp ").append(ranks.get(Constants.rankNum - i)).append("<br>");
        }
        str.append("</div><body></html>");
        lblRank.setText(str.toString());
    }

}
