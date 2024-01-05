package gui;

import app.Movie;
import repository.MovieRepository;

import javax.swing.*;
import java.util.List;

public class ActorInterface implements Displayable{
    private final MovieRepository movieRepository = new MovieRepository();

    /**
     * displays actor name in the provided JTextArea based on a given string condition
     *
     * @param textArea the JTextArea where movie information will be displayed
     * @param stringCondition the condition used to filter and display movie information
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
