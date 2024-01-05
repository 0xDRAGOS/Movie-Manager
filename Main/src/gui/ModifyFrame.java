package gui;

import javax.swing.*;
import java.awt.*;

public class ModifyFrame extends JFrame {

    public ModifyFrame(){
        setLayout(new FlowLayout());
        ModifyPanel modifyPanel = new ModifyPanel();
        add(modifyPanel);

        setTitle("Modify movie");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); //setting frame's location in the middle of the screen
        pack();
        setVisible(true);
    }
}
