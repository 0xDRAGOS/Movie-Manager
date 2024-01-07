package utility;

import entity.Movie;
import repository.MovieRepository;

import javax.swing.*;
import java.util.List;

/**
 * provides a method to display movies based on actor names in a JTextArea
 *
 * @author Simion Dragos Ionut
 */
public class ActorDisplay implements Displayable{
    private final MovieRepository movieRepository = new MovieRepository();

    /**
     * displays movies in the provided JTextArea based on the given actor name condition
     *
     * @param textArea       the JTextArea where the movie information will be displayed
     * @param stringCondition the condition specifying the actor name
     */
    public void display(JTextArea textArea, String stringCondition){
        textArea.setText("");
        textArea.setEditable(false);
        int index = stringCondition.lastIndexOf(' ');

        if(index >= 0) {
            String firstName = stringCondition.substring(0, index);
            String lastName = stringCondition.substring(index + 1);

            List<Movie> movies = movieRepository.getMoviesByActorName(firstName, lastName);
            for (Movie item : movies) {
                textArea.append(item.toStringLine() + "\n");
            }
        }
    }
}
