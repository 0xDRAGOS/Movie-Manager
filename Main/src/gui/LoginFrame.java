package gui;

import javax.swing.*;

public class LoginFrame extends JFrame {
    private LoginPanel loginPanel;
    public LoginFrame(){
        super("Login Menu");

        loginPanel = new LoginPanel();
        add(loginPanel);

        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //setting frame's location in the middle of the screen
        setResizable(false);
        setVisible(true);
    }
}
