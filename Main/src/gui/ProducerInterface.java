package gui;

import app.Movie;
import repository.MovieRepository;

import javax.swing.*;
import java.util.List;

public class ProducerInterface implements Displayable{
    MovieRepository movieRepository = new MovieRepository();
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
