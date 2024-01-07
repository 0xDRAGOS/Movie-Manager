package utility;

import entity.Movie;
import repository.MovieRepository;

import javax.swing.*;
import java.util.List;

/**
 * provides a method to display movies based on production company in a JTextArea
 *
 * @author Simion Dragos Ionut
 */
public class ProducerDisplay implements Displayable{
    MovieRepository movieRepository = new MovieRepository();

    /**
     * displays movies in the provided JTextArea based on a production company condition
     *
     * @param textArea       the JTextArea where the movie information will be displayed
     * @param stringCondition the condition specifying the actor name
     */
    public void display(JTextArea textArea, String stringCondition) {
        textArea.setText("");
        textArea.setEditable(false);
        List<Movie> movies = movieRepository.getMoviesByProducer(stringCondition);
        for (Movie item : movies) {
            textArea.append(item.toStringLine() + "\n");
        }
    }
}
