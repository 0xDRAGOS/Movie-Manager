package gui;

import app.Movie;
import app.MovieManager;
import repository.MovieRepository;

import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PageStartPanel extends JPanel {
    private MovieRepository movieRepository;
    private MovieManager movieManager;
    private JTextArea textArea;
    private JButton addMovieButton;
    private JButton modifyMovieButton;
    private JButton deleteMovieButton;
    private JButton createRaportButton;
    public PageStartPanel(){
        setLayout(new FlowLayout(FlowLayout.LEFT));

        movieRepository = new MovieRepository();
        movieManager = new MovieManager();
        textArea = new JTextArea("Movie manager: ");
        addMovieButton = new JButton("Add");
        modifyMovieButton = new JButton("Modify");
        deleteMovieButton = new JButton("Delete");
        createRaportButton = new JButton("Create Raport");

        textArea.setEditable(false);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem addMenuItem = new JMenuItem("Add");
        JMenuItem modifyMenuItem = new JMenuItem("Modify");
        JMenuItem deleteMenuItem = new JMenuItem("Delete");
        JMenuItem createRaportMenuItem = new JMenuItem("Create Raport");

        menu.add(addMenuItem);
        menu.add(modifyMenuItem);
        menu.add(deleteMenuItem);
        menu.add(createRaportMenuItem);
        menuBar.add(menu);
        //add(textArea);
        add(menuBar);
        add(addMovieButton);
        add(modifyMovieButton);
        add(deleteMovieButton);
        add(createRaportButton);

        // menu items
        addMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        addMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddFrame();
            }
        });

        modifyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        modifyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModifyFrame();
            }
        });

        deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteFrame();
            }
        });

        createRaportMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        createRaportMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Movie> movieList = movieRepository.getMovies();
                movieManager.addAll(movieList);

                if(!movieManager.createRaport("Raports\\movies_raport.txt")){
                    JOptionPane.showMessageDialog(PageStartPanel.this, "Could not create movie raport.", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(PageStartPanel.this, "Movie raport created successfully.", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // buttons
        addMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddFrame();
            }
        });

        deleteMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteFrame();
            }
        });

        modifyMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModifyFrame();
            }
        });

        createRaportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Movie> movieList = movieRepository.getMovies();
                movieManager.addAll(movieList);

                if(!movieManager.createRaport("Raports\\movies_raport.txt")){
                    JOptionPane.showMessageDialog(PageStartPanel.this, "Could not create movie raport.", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(PageStartPanel.this, "Movie raport created successfully.", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
