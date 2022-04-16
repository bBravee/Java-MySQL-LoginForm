package com.javaDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginForm extends JFrame {

    final private Font font = new Font("Open Sans", Font.BOLD, 18);

    JTextField tfEmail;
    JPasswordField pfPassword;

    public void initialize() {

        ImageIcon image = new ImageIcon(getClass().getResource("login.png"));
        JLabel iconLabel = new JLabel(image, SwingConstants.CENTER);


        // Form panel

        JLabel lbEmail = new JLabel("Email", SwingConstants.CENTER);
        JLabel lbPassword = new JLabel("Password", SwingConstants.CENTER);



        lbEmail.setFont(font);
        lbPassword.setFont(font);

        tfEmail = new JTextField();
        tfEmail.setFont(font);

        pfPassword = new JPasswordField();
        pfPassword.setFont(font);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30,50,30,50));
        formPanel.add(iconLabel);
        formPanel.add(lbEmail);
        formPanel.add(tfEmail);
        formPanel.add(lbPassword);
        formPanel.add(pfPassword);



        // Login button

        JButton jbtLogin = new JButton("Login");
        jbtLogin.setFont(font);


        // Login button actionListener

        jbtLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                User user = getAuthenticatedUser(email, password);

                if(user != null) {
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.initialize(user);
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Email or password invalid!", "Try again", JOptionPane.ERROR_MESSAGE );
                }
            }
        });


        // Cancel Button

        JButton jbtCancel = new JButton("Cancel");
        jbtCancel.setFont(font);

        // Cancel button action listener

        jbtCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Destroys LoginForm
                dispose();

            }
        });

        // Panel with buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 10));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30,50,30,50));
        buttonsPanel.add(jbtLogin);
        buttonsPanel.add(jbtCancel);


        // Initialization of the frame

        add(formPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Login Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,600);
        setMinimumSize(new Dimension(350,450));
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }


    // Method that returns authenticated user from DB

    private User getAuthenticatedUser(String email, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost:3306/your_schema_name";
        final String USERNAME = "your_login";
        final String PASSWORD = "your_password";

        try {
            Connection connect = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String sql = "SELECT * FROM login_form WHERE email=? AND password=?";
            PreparedStatement preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone_number");
                user.adress = resultSet.getString("adress");
                user.password = resultSet.getString("password");
            }

            preparedStatement.close();
            connect.close();

        } catch(Exception e) {
            System.out.println("Connection failed!");
        }

        return user;
    }
}


