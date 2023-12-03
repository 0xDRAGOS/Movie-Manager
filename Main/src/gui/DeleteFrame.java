package gui;

import javax.swing.*;
import java.awt.*;

public class DeleteFrame extends JFrame {
    private DeletePanel deletePanel;
    public DeleteFrame() {
        setLayout(new FlowLayout());
        deletePanel = new DeletePanel();
        add(deletePanel);

        setTitle("Delete movie");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); //setting frame's location in the middle of the screen
        pack();
        setVisible(true);
    }
}