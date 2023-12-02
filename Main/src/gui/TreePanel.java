package gui;

import repository.ActorRepository;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class TreePanel extends JPanel {
    public TreePanel() {
        ActorRepository actorRepository = new ActorRepository();
        List<String> actors = actorRepository.getActors();
        //setLayout();
        DefaultMutableTreeNode actorsTree = new DefaultMutableTreeNode("Actors");
        for(String item : actors){
            DefaultMutableTreeNode actorsTreeChild = new DefaultMutableTreeNode(item);
            actorsTree.add(actorsTreeChild);
        }
        JTree tree = new JTree(actorsTree);
        add(tree);
    }
}
