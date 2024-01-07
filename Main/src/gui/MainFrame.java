package gui;

import utility.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;

/**
 * provides main UI frame for application
 *
 * @author Simion Dragos Ionut
 */
public class MainFrame extends JFrame {
    private final TreePanel treePanel;
    private final JTextArea movieDetailsTextArea;
    private final JPanel rightPanel;
    private final JTable moviesTable;

    public MainFrame(){
        setLayout(new BorderLayout());

        PageStartPanel pageStartPanel = new PageStartPanel();
        treePanel = new TreePanel();
        movieDetailsTextArea = new JTextArea();
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        moviesTable = new JTable();

        movieDetailsTextArea.setEditable(false);

        add(pageStartPanel, BorderLayout.PAGE_START);

        JSplitPane splitPane = new JSplitPane(SwingConstants.VERTICAL, new JScrollPane(treePanel), new JScrollPane(rightPanel));

        add(splitPane);

        treePanel.addTreeSelectionListener(e -> {
            TreePath selectedPath = treePanel.getSelectedPath();
            if(selectedPath != null){
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                rightPanel.removeAll();
                if (selectedNode.getUserObject() instanceof String) {
                    String nodeName = (String) selectedNode.getUserObject();

                    if(selectedNode.getParent() != null){
                        switch (selectedNode.getParent().toString()){
                            case "Genres":
                                rightPanel.add(moviesTable);
                                GenreDisplay genreDisplay = new GenreDisplay();
                                genreDisplay.displayTable(moviesTable, nodeName);
                                break;
                            case "Movies":
                                JPanel movieDetailsPlayPanel = new JPanel();
                                MovieDisplay movieDisplay = new MovieDisplay();
                                movieDisplay.displayButton(movieDetailsPlayPanel, nodeName);
                                rightPanel.add(movieDetailsPlayPanel);
                                break;
                            case "Actors":
                                rightPanel.add(movieDetailsTextArea);
                                ActorDisplay actorDisplay = new ActorDisplay();
                                actorDisplay.display(movieDetailsTextArea, nodeName);
                                break;
                            case "Directors":
                                rightPanel.add(movieDetailsTextArea);
                                DirectorDisplay directorDisplay = new DirectorDisplay();
                                directorDisplay.display(movieDetailsTextArea, nodeName);
                                break;
                            case "Producers":
                                rightPanel.add(movieDetailsTextArea);
                                ProducerDisplay producerDisplay = new ProducerDisplay();
                                producerDisplay.display(movieDetailsTextArea, nodeName);
                                break;
                        }
                    }
                }
                rightPanel.revalidate();
                rightPanel.repaint();
            }
        });
        splitPane.setDividerLocation(200);
        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        setTitle("Movie Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // setting frame's location in the middle of the screen
        setSize(800, 600);
        setVisible(true);
    }
}
