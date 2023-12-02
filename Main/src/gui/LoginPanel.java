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
    public LoginPanel(){
        setLayout(new GridLayout(3, 1));

        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
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
                    MainFrame mainFrame = new MainFrame();
                    //                    JFrame frame = new JFrame();
//                    frame.setSize(1000, 1000);
//                    frame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong password or user!");
                }
            }
        });

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
    }
}
