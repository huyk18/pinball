package ui;

import gameservice.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Help extends Dialog {
    Help() {
        JLabel lblHelp = new JLabel(Constants.textHelp, JLabel.LEFT);
        lblHelp.setFont(new Font("Arial", Font.BOLD, 12));
        lblHelp.setBounds(33, 35, 254, 400);
        lblHelp.setForeground(Color.white);
        add(lblHelp,0);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame.showMenu();
            }
        });
    }
}
