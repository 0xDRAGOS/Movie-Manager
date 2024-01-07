package utility;

import entity.Movie;
import repository.MovieRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * provides a method to display movies based on genre in a JTextArea/JTable
 *
 * @author Simion Dragos Ionut
 */
public class GenreDisplay implements Displayable{
    private final MovieRepository movieRepository = new MovieRepository();

    /**
     * displays movies in the provided JTextArea based on a genre condition
     *
     * @param textArea       the JTextArea where the movie information will be displayed
     * @param stringCondition the condition specifying the actor name
     */
    public void display(JTextArea textArea, String stringCondition) {
        textArea.setText("");
        List<Movie> movies = movieRepository.getMoviesByGenre(stringCondition);
            for (Movie item : movies) {
                textArea.append(item.toString() + "\n");
            }
    }

    /**
     * displays movies in the provided JTable based on a genre condition
     *
     * @param moviesTable       the JTable where the movie information will be displayed
     * @param stringCondition the condition specifying the actor name
     */
    public void displayTable(JTable moviesTable, String stringCondition){
        DefaultTableModel tableModel = new DefaultTableModel();
        moviesTable.setDefaultEditor(Object.class, null);
        String[] columnNames = {"Title", "Genre", "Launch date", "Rating", "Actors", "Director", "Producer"};
        tableModel.setColumnIdentifiers(columnNames);
        List<Movie> movies = movieRepository.getMoviesByGenre(stringCondition);
        for (Movie item : movies) {
            tableModel.addRow(item.toStringTable());
        }
        moviesTable.setModel(tableModel);
    }
}
