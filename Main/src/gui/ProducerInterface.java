package gui;

import app.Movie;
import repository.MovieRepository;

import javax.swing.*;
import java.util.List;

public class ProducerInterface implements Displayable{
    private final MovieRepository movieRepository = new MovieRepository();

    /**
     * displays movie information in the provided JTextArea based on a given producer's name
     *
     * @param textArea the JTextArea where movie information will be displayed
     * @param stringCondition the producer's name used to filter and display movie information
     */
    @Override
    public void display(JTextArea textArea, String stringCondition) {
        textArea.setText("");
        textArea.setEditable(false);
        List<Movie> movies = movieRepository.getMoviesByProducer(stringCondition);
        for (Movie item : movies) {
            textArea.append(item.toStringLine() + "\n");
        }
    }
}
