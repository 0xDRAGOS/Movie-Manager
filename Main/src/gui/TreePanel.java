package gui;

import repository.*;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.List;

/**
 * provides UI panel for displaying a tree structure
 *
 * @author Simion Dragos Ionut
 */
public class TreePanel extends JPanel {
    private final JTree tree;
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

        MovieRepository movieRepository = new MovieRepository();
        List<String> moviesName = movieRepository.getMoviesName();
        DefaultMutableTreeNode moviesTree = new DefaultMutableTreeNode("Movies");
        for(String item : moviesName){
            DefaultMutableTreeNode moviesTreeChild = new DefaultMutableTreeNode(item);
            moviesTree.add(moviesTreeChild);
        }
        root.add(moviesTree);

        ActorRepository actorRepository = new ActorRepository();
        List<String> actors = actorRepository.getActors();
        DefaultMutableTreeNode actorsTree = new DefaultMutableTreeNode("Actors");
        for(String item : actors){
            DefaultMutableTreeNode actorsTreeChild = new DefaultMutableTreeNode(item);
            actorsTree.add(actorsTreeChild);
        }
        root.add(actorsTree);

        DirectorRepository directorRepository = new DirectorRepository();
        List<String> directors = directorRepository.getDirectors();
        DefaultMutableTreeNode directorsTree = new DefaultMutableTreeNode("Directors");
        for (String item : directors){
            DefaultMutableTreeNode directorTreeChild = new DefaultMutableTreeNode(item);
            directorsTree.add(directorTreeChild);
        }
        root.add(directorsTree);

        ProducerRepository producerRepository = new ProducerRepository();
        List<String> producers = producerRepository.getProducers();
        DefaultMutableTreeNode producersTree = new DefaultMutableTreeNode("Producers");
        for(String item : producers){
            DefaultMutableTreeNode producersTreeChild = new DefaultMutableTreeNode(item);
            producersTree.add(producersTreeChild);
        }
        root.add(producersTree);

        tree = new JTree(root);
        add(tree);
    }

    /**
     * adds a TreeSelectionListener to the JTree component
     *
     * @param listener the TreeSelectionListener to be added
     */
    public void addTreeSelectionListener(TreeSelectionListener listener){
        tree.addTreeSelectionListener(listener);
    }

    /**
     * gets the selected TreePath from the JTree component
     *
     * @return the selected TreePath
     */
    public TreePath getSelectedPath(){
        return tree.getSelectionPath();
    }
}
