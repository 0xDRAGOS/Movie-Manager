package gui;

import app.Movie;
import app.MovieManager;
import repository.MovieRepository;

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

        //add(textArea);
        add(addMovieButton);
        add(modifyMovieButton);
        add(deleteMovieButton);
        add(createRaportButton);

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

        createRaportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Movie> movieList = movieRepository.getMovies();
                movieManager.addAll(movieList);

                if(movieManager.createRaport("Raports\\movies_raport.txt")){
                    JOptionPane.showMessageDialog(PageStartPanel.this, "Could not create movie raport.", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(PageStartPanel.this, "Movie raport created successfully.", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
