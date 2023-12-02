package gui;

import app.Movie;
import repository.MovieRepository;

import javax.swing.*;
import java.util.List;

public class GenreInterface implements Displayable{
    MovieRepository movieRepository = new MovieRepository();
    @Override
    public void display(JTextArea textArea, String stringCondition) {
        textArea.setText("");
        List<Movie> movies = movieRepository.getMoviesByGenre(stringCondition);
            for (Movie item : movies) {
                textArea.append(item.toString() + "\n");
            }
    }
}
