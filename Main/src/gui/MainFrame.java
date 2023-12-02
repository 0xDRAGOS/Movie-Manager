package gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;

public class MainFrame extends JFrame {
    private TreePanel treePanel;
    private JTextArea movieDetailsTextArea;
    public MainFrame(){
        setLayout(new BorderLayout());
        treePanel = new TreePanel();
        movieDetailsTextArea = new JTextArea();
        movieDetailsTextArea.setEditable(false);
        JSplitPane splitPane = new JSplitPane(SwingConstants.VERTICAL, treePanel, new JScrollPane(movieDetailsTextArea));
        add(splitPane);

        treePanel.addTreeSelectionListener(e -> {
            TreePath selectedPath = treePanel.getSelectedPath();
            if(selectedPath != null){
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                if (selectedNode.getUserObject() instanceof String) {
                    String nodeName = (String) selectedNode.getUserObject();

                    if(selectedNode.getParent() != null){
                        switch (selectedNode.getParent().toString()){
                            case "Genres":
                                GenreInterface genreInterface = new GenreInterface();
                                genreInterface.display(movieDetailsTextArea, nodeName);
                                break;
                            case "Actors":
                                ActorInterface actorInterface = new ActorInterface();
                                actorInterface.display(movieDetailsTextArea, nodeName);
                                break;
                        }
                    }
                }
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //setting frame's location in the middle of the screen
        setSize(800, 600);
        setVisible(true);
    }
    private void displayMoviesForActor(String actorName) {
        // Implement logic to fetch and display movies for the selected actor
        // You can use the actorName to query the database and update the movieDetailsTextArea
        // For now, let's just display a placeholder message
        movieDetailsTextArea.setText("Movies for actor: " + actorName);
    }
}
