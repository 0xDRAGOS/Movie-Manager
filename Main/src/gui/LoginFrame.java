package gui;

import javax.swing.*;

public class LoginFrame extends JFrame {
    public LoginFrame(){
        super("Login");

        LoginPanel loginPanel = new LoginPanel(this);
        add(loginPanel);

        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //setting frame's location in the middle of the screen
        setResizable(false);
        setVisible(true);
    }

    /**
     * closes the frame by setting its visibility to false and disposing of it
     */
    public void closeFrame() {
        setVisible(false);
        dispose();
    }
}
