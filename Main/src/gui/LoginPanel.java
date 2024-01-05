package gui;

import repository.UserRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginPanel(LoginFrame loginFrame){

        setLayout(new BorderLayout());
        JPanel loginPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(2,2));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel usernameLabel = new JLabel("Username: ", SwingConstants.CENTER);
        JLabel passwordLabel = new JLabel("Password: ", SwingConstants.CENTER);
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                UserRepository userRep = new UserRepository();
                if(userRep.isValid(username, password)){
                    JOptionPane.showMessageDialog(null, "Logged in successfully!");
                    loginFrame.closeFrame();
                    MainFrame mainFrame = new MainFrame();

                } else {
                    JOptionPane.showMessageDialog(null, "Wrong password or user!");
                }
            }
        });

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        buttonPanel.add(loginButton);
        add(loginPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
