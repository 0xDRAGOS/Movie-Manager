package gui;

import repository.GenreRepository;
import repository.MovieRepository;
import repository.PersonRepository;
import repository.ProducerRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPanel extends JPanel {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final PersonRepository personRepository;
    private final ProducerRepository producerRepository;
    private final JTextField titleField;
    private final JTextField launchDateField;
    private final JTextField ratingField;
    private final JTextField genreField;
    private final JTextField actorsField;
    private final JTextField directorField;
    private final JTextField producerField;

    public AddPanel(){
        setLayout(new GridLayout(8, 2, 10, 10)); // 4 rows, 2 columns, 10 pixels horizontal and vertical gap

        personRepository = new PersonRepository();
        producerRepository = new ProducerRepository();
        genreRepository = new GenreRepository();
        movieRepository = new MovieRepository();

        JLabel titleLabel = new JLabel("Title:", SwingConstants.CENTER);
        titleField = new JTextField();

        JLabel launchDateLabel = new JLabel("Launch Date (dd/mm/yyyy):", SwingConstants.CENTER);
        launchDateField = new JTextField();

        JLabel ratingLabel = new JLabel("Rating:", SwingConstants.CENTER);
        ratingField = new JTextField();

        JLabel genreLabel = new JLabel("Genre:", SwingConstants.CENTER);
        genreField = new JTextField();

        JLabel actorsLabel = new JLabel("Actors (actor1, actor2, actor3 etc):", SwingConstants.CENTER);
        actorsField = new JTextField();

        JLabel directorLabel = new JLabel("Director:", SwingConstants.CENTER);
        directorField = new JTextField();

        JLabel producerLabel = new JLabel("Producer", SwingConstants.CENTER);
        producerField = new JTextField();

        JButton addButton = new JButton("Add");
        JButton resetButton = new JButton("Reset");

        add(titleLabel);
        add(titleField);
        add(launchDateLabel);
        add(launchDateField);
        add(ratingLabel);
        add(ratingField);
        add(genreLabel);
        add(genreField);
        add(actorsLabel);
        add(actorsField);
        add(directorLabel);
        add(directorField);
        add(producerLabel);
        add(producerField);
        add(resetButton);
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String launchDateString = launchDateField.getText();
                String ratingText = ratingField.getText();
                String genre = genreField.getText();
                String actors = actorsField.getText();
                String director = directorField.getText();
                String producer = producerField.getText();

                // input validation
                if (title.isEmpty() || launchDateString.isEmpty() || ratingText.isEmpty() || genre.isEmpty()
                        || actors.isEmpty() || director.isEmpty() || producer.isEmpty()) {
                    JOptionPane.showMessageDialog(AddPanel.this, "Empty field/s.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // parse rating after validation
                float rating;
                try {
                    rating = Float.parseFloat(ratingText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddPanel.this, "Invalid rating format.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Date launchDate;
                try {
                    launchDate = new SimpleDateFormat("dd/MM/yyyy").parse(launchDateString);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(AddPanel.this, "Invalid date format. Please use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int movieID = movieRepository.insertIntoDatabase(title, new java.sql.Date(launchDate.getTime()), rating);

                if (movieID != -1) {
                    // handle actors
                    String[] actorsArray = actors.split(",\\s*");
                    for (String actor : actorsArray) {
                        int index = actor.indexOf(" ");
                        String firstName = actor.substring(0, index);
                        String lastName = actor.substring(index + 1);
                        if (!personRepository.exists(lastName, firstName)) {
                            personRepository.insertIntoDatabase(lastName, firstName);
                        }
                        int personID = personRepository.getPersonID(lastName, firstName);
                        movieRepository.insertMovieActorIntoDatabase(movieID, personID);
                    }

                    // handle genre
                    if (!genreRepository.exists(genre)) {
                        genreRepository.insertIntoDatabase(genre);
                    }
                    int genreID = genreRepository.getGenreID(genre);
                    movieRepository.insertMovieGenreIntoDatabase(movieID, genreID);

                    // handle producer
                    if (!producerRepository.exists(producer)) {
                        producerRepository.insertIntoDatabase(producer);
                    }
                    int producerID = producerRepository.getProducerID(producer);
                    movieRepository.insertMovieProducerIntoDatabase(movieID, producerID);

                    // handle director
                    int index = director.indexOf(" ");
                    String directorFirstName = director.substring(0, index);
                    String directorLastName = director.substring(index + 1);
                    if (!personRepository.exists(directorLastName, directorFirstName)) {
                        personRepository.insertIntoDatabase(directorLastName, directorFirstName);
                    }
                    int directorID = personRepository.getPersonID(directorLastName, directorFirstName);
                    movieRepository.insertMovieDirectorIntoDatabase(movieID, directorID);

                    JOptionPane.showMessageDialog(AddPanel.this, "Movie added successfully.", "", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(AddPanel.this, "Could not add the movie.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                titleField.setText("");
                launchDateField.setText("");
                ratingField.setText("");
                genreField.setText("");
                actorsField.setText("");
                directorField.setText("");
                producerField.setText("");
            }
        });
    }
}
