package gui;

import app.Movie;
import repository.MovieRepository;

import javax.swing.*;
import java.util.List;

public class DirectorInterface implements Displayable{
    private MovieRepository movieRepository = new MovieRepository();
    public void display(JTextArea textArea, String stringCondition){
        textArea.setText("");
        textArea.setEditable(false);
        int index = stringCondition.lastIndexOf(' ');

        if(index >= 0) {
            String firstName = stringCondition.substring(0, index);
            String lastName = stringCondition.substring(index + 1);

            List<Movie> movies = movieRepository.getMoviesByDirectorName(firstName, lastName);
            for (Movie item : movies) {
                textArea.append(item.toStringLine() + "\n");
            }
        }
    }
}
