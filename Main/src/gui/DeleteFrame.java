package gui;

import javax.swing.*;
import java.awt.*;

/**
 * provides UI frame for deleting movies
 *
 * @author Simion Dragos Ionut
 */
public class DeleteFrame extends JFrame {
    public DeleteFrame() {
        setLayout(new FlowLayout());
        DeletePanel deletePanel = new DeletePanel();
        add(deletePanel);

        setTitle("Delete movie");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); //setting frame's location in the middle of the screen
        pack();
        setVisible(true);
    }
}
