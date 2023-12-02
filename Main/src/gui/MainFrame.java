package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(){
        setLayout(new BorderLayout());
        TreePanel treePanel = new TreePanel();
        TreePanel test = new TreePanel();
        JSplitPane splitPane = new JSplitPane(SwingConstants.VERTICAL, treePanel, test);
        add(splitPane);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //setting frame's location in the middle of the screen
        setSize(800, 600);
        setVisible(true);
    }
}
