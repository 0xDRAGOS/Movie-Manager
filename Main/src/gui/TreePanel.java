package gui;

import repository.ActorRepository;
import repository.GenreRepository;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.List;

public class TreePanel extends JPanel {
    private JTree tree;
    public TreePanel() {
        setLayout(new BorderLayout());
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();

        GenreRepository genreRepository = new GenreRepository();
        List<String> genres = genreRepository.getGenres();
        DefaultMutableTreeNode genresTree = new DefaultMutableTreeNode("Genres");
        for(String item : genres){
            DefaultMutableTreeNode genresTreeChild = new DefaultMutableTreeNode(item);
            genresTree.add(genresTreeChild);
        }
        root.add(genresTree);

        ActorRepository actorRepository = new ActorRepository();
        List<String> actors = actorRepository.getActors();
        DefaultMutableTreeNode actorsTree = new DefaultMutableTreeNode("Actors");
        for(String item : actors){
            DefaultMutableTreeNode actorsTreeChild = new DefaultMutableTreeNode(item);
            actorsTree.add(actorsTreeChild);
        }
        root.add(actorsTree);

        tree = new JTree(root);
        add(tree);
    }

    public void addTreeSelectionListener(TreeSelectionListener listener){
        tree.addTreeSelectionListener(listener);
    }

    public TreePath getSelectedPath(){
        return tree.getSelectionPath();
    }
}
