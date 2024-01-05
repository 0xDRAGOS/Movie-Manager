package gui;

import app.Movie;
import repository.MovieRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GenreInterface implements Displayable{
    MovieRepository movieRepository = new MovieRepository();

    /**
     * displays movie information in the provided JTextArea based on a given genre
     *
     * @param textArea the JTextArea where movie information will be displayed
     * @param stringCondition the genre used to filter and display movie information
     */
    @Override
    public void display(JTextArea textArea, String stringCondition) {
        textArea.setText("");
        List<Movie> movies = movieRepository.getMoviesByGenre(stringCondition);
            for (Movie item : movies) {
                textArea.append(item.toString() + "\n");
            }
    }

    /**
     * displays movie information in the provided JTable based on a given genre
     *
     * @param moviesTable the JTable where movie information will be displayed
     * @param stringCondition the genre used to filter and display movie information
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
