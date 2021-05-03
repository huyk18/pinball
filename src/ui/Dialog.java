package ui;

import gameservice.Constants;

import javax.swing.*;

public class Dialog extends JPanel {
    JButton btn;
    JLabel lblBackGround;

    Dialog() {
        super(null, true);
        btn = new JButton();
        lblBackGround = new JLabel(new ImageIcon(Constants.imageDialogBGFileName));
        btn.setBounds(86, 441, 148, 54);
        btn.setContentAreaFilled(false);
        lblBackGround.setBounds(0, 0, 320, 568);
        add(btn);
        add(lblBackGround);
    }
}
