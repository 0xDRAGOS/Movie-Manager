package gui;

import app.Movie;
import repository.MovieRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
//    public void displayTable(JTextArea textArea, String stringCondition){
//        textArea.setText("");
//        JTable moviesTable = new JTable();
//        DefaultTableModel tableModel = new DefaultTableModel();
//        moviesTable.setDefaultEditor(Object.class, null);
//        String[] columnNames = {"Title", "Genre", "Launch date", "Rating", "Actors", "Director", "Producer"};
//        tableModel.setColumnIdentifiers(columnNames);
//        List<Movie> movies = movieRepository.getMoviesByGenre(stringCondition);
//        for (Movie item : movies) {
//            tableModel.addRow(item.toStringTable());
//        }
//
//        moviesTable.setModel(tableModel);
//
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.add(new JScrollPane(moviesTable), BorderLayout.CENTER);
//        panel.add(textArea, BorderLayout.NORTH);
//
//        JFrame frame = new JFrame("Movies");
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setLayout(new BorderLayout());
//        frame.add(panel, BorderLayout.CENTER);
//        frame.setSize(400, 300);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }

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
