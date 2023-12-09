package gui;

import repository.UserRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel loginPanel;
    private JPanel buttonPanel;
    private LoginFrame loginFrame;
    public LoginPanel(LoginFrame loginFrame){
        this.loginFrame = loginFrame;

        setLayout(new BorderLayout());
        loginPanel = new JPanel();
        buttonPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(2,2));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        usernameLabel = new JLabel("Username: ", SwingConstants.CENTER);
        passwordLabel = new JLabel("Password: ", SwingConstants.CENTER);
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");

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
