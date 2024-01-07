package gui;

import javax.swing.*;
import java.awt.*;

/**
 * provides UI frame for adding movies
 *
 * @author Simion Dragos Ionut
 */
public class AddFrame extends JFrame {
    public AddFrame(){
        setLayout(new FlowLayout());
        AddPanel addPanel = new AddPanel();
        add(addPanel);

        setTitle("Add movie");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); //setting frame's location in the middle of the screen
        setSize(500,350);
        setVisible(true);
    }
}
