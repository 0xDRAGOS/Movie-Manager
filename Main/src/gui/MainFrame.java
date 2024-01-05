package gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;

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
                                GenreInterface genreInterface = new GenreInterface();
                                genreInterface.displayTable(moviesTable, nodeName);
                                break;
                            case "Movies":
                                JPanel movieDetailsPlayPanel = new JPanel();
                                MovieInterface movieInterface = new MovieInterface();
                                movieInterface.displayButton(movieDetailsPlayPanel, nodeName);
                                rightPanel.add(movieDetailsPlayPanel);
                                break;
                            case "Actors":
                                rightPanel.add(movieDetailsTextArea);
                                ActorInterface actorInterface = new ActorInterface();
                                actorInterface.display(movieDetailsTextArea, nodeName);
                                break;
                            case "Directors":
                                rightPanel.add(movieDetailsTextArea);
                                DirectorInterface directorInterface = new DirectorInterface();
                                directorInterface.display(movieDetailsTextArea, nodeName);
                                break;
                            case "Producers":
                                rightPanel.add(movieDetailsTextArea);
                                ProducerInterface producerInterface = new ProducerInterface();
                                producerInterface.display(movieDetailsTextArea, nodeName);
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
        setLocationRelativeTo(null); //setting frame's location in the middle of the screen
        setSize(800, 600);
        setVisible(true);
    }
}
