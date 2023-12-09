package gui;

import javax.swing.*;

public class LoginFrame extends JFrame {
    private LoginPanel loginPanel;
    public LoginFrame(){
        super("Login");

        loginPanel = new LoginPanel(this);
        add(loginPanel);

        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //setting frame's location in the middle of the screen
        setResizable(false);
        setVisible(true);
    }

    public void closeFrame() {
        setVisible(false);
        dispose();
    }
}
