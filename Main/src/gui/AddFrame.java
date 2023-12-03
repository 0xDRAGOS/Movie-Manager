package gui;

import javax.swing.*;
import java.awt.*;

public class AddFrame extends JFrame {
    private AddPanel addPanel;
    public AddFrame(){
        setLayout(new FlowLayout());
        addPanel = new AddPanel();
        add(addPanel);

        setTitle("Add movie");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); //setting frame's location in the middle of the screen
        setSize(500,350);
        setVisible(true);
    }
}
