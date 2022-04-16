package com.javaDB;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public void initialize(User user) {

        // Panel with info about user
        JPanel infPanel = new JPanel();
        infPanel.setLayout(new GridLayout(0, 2, 5, 5));
        infPanel.setBorder(BorderFactory.createEmptyBorder(30,50,30,50));
        infPanel.add(new JLabel("Name"));
        infPanel.add(new JLabel(user.name));
        infPanel.add(new JLabel("Email"));
        infPanel.add(new JLabel(user.email));
        infPanel.add(new JLabel("Phone"));
        infPanel.add(new JLabel(user.phone));
        infPanel.add(new JLabel("Adress"));
        infPanel.add(new JLabel(user.adress));

        add(infPanel, BorderLayout.NORTH);

        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1100, 650));
        // Window is centered on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
