package gui;

import app.Movie;
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

public class ModifyPanel extends JPanel {
    private MovieRepository movieRepository;
    private GenreRepository genreRepository;
    private PersonRepository personRepository;
    private ProducerRepository producerRepository;
    private JLabel movieTitleLabel;
    private JTextField movieTitleField;
    private JLabel newTitleLabel;
    private JTextField newTitleField;
    private JLabel newLaunchDateLabel;
    private JTextField newLaunchDateField;
    private JLabel newRatingLabel;
    private JTextField newRatingField;
    private JLabel newGenreLabel;
    private JTextField newGenreField;
    private JLabel newActorsLabel;
    private JTextField newActorsField;
    private JLabel newDirectorLabel;
    private JTextField newDirectorField;
    private JLabel newProducerLabel;
    private JTextField newProducerField;
    private JButton searchButton;
    private JButton modifyButton;
    private JPanel searchPanel;
    private JPanel modifyPanel;
    private JPanel modifyButtonPanel;
    public ModifyPanel(){
        movieRepository = new MovieRepository();
        genreRepository = new GenreRepository();
        personRepository = new PersonRepository();
        producerRepository = new ProducerRepository();

        setLayout(new CardLayout());

        searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        movieTitleLabel = new JLabel("Title:");
        movieTitleField = new JTextField(15);
        searchButton = new JButton("Search");

        searchPanel.add(movieTitleLabel);
        searchPanel.add(movieTitleField);
        searchPanel.add(searchButton);

        modifyPanel = new JPanel();
        modifyPanel.setLayout(new GridLayout(7, 2));

        newTitleLabel = new JLabel("Title:", SwingConstants.CENTER);
        newTitleField = new JTextField(SwingConstants.LEFT);

        newLaunchDateLabel = new JLabel("Launch Date (dd/mm/yyyy):", SwingConstants.CENTER);
        newLaunchDateField = new JTextField(SwingConstants.LEFT);

        newRatingLabel = new JLabel("Rating:", SwingConstants.CENTER);
        newRatingField = new JTextField(SwingConstants.LEFT);

        newGenreLabel = new JLabel("Genre:", SwingConstants.CENTER);
        newGenreField = new JTextField(SwingConstants.LEFT);

        newActorsLabel = new JLabel("Actors (actor1, actor2, actor3 etc):", SwingConstants.CENTER);
        newActorsField = new JTextField(SwingConstants.LEFT);

        newDirectorLabel = new JLabel("Director:", SwingConstants.CENTER);
        newDirectorField = new JTextField(SwingConstants.LEFT);

        newProducerLabel = new JLabel("Producer", SwingConstants.CENTER);
        newProducerField = new JTextField(SwingConstants.LEFT);
        modifyButton = new JButton("Modify");

        modifyButtonPanel = new JPanel();
        modifyButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        modifyButtonPanel.add(modifyButton);

        modifyPanel.add(newTitleLabel);
        modifyPanel.add(newTitleField);
        modifyPanel.add(newLaunchDateLabel);
        modifyPanel.add(newLaunchDateField);
        modifyPanel.add(newRatingLabel);
        modifyPanel.add(newRatingField);
        modifyPanel.add(newGenreLabel);
        modifyPanel.add(newGenreField);
        modifyPanel.add(newActorsLabel);
        modifyPanel.add(newActorsField);
        modifyPanel.add(newDirectorLabel);
        modifyPanel.add(newDirectorField);
        modifyPanel.add(newProducerLabel);
        modifyPanel.add(newProducerField);

        JPanel combinedPanel = new JPanel();
        combinedPanel.setLayout(new BorderLayout());
        combinedPanel.add(modifyPanel, BorderLayout.CENTER);
        combinedPanel.add(modifyButtonPanel, BorderLayout.SOUTH);

        add(searchPanel);
        add(combinedPanel, "combined");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String movieTitle = movieTitleField.getText();
                int movieID = movieRepository.getMovieIDByTitle(movieTitle);
                if(movieID < 0){
                    JOptionPane.showMessageDialog(ModifyPanel.this, "The movie was not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }else {
                    Movie movie = movieRepository.getMovieByTitle(movieTitle);
                    newTitleField.setText(movie.getTitle());
                    newActorsField.setText(movie.getActors());
                    newDirectorField.setText(movie.getDirector());
                    newGenreField.setText(movie.getGenre());
                    newLaunchDateField.setText(movie.getLaunchDate().toString());
                    newProducerField.setText(movie.getProducer());
                    newRatingField.setText(String.valueOf(movie.getRating()));
                    CardLayout layout = (CardLayout) getLayout();
                    layout.show(ModifyPanel.this, "combined");

                    modifyButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String title = newTitleField.getText();
                            String launchDateString = newLaunchDateField.getText();
                            String ratingText = newRatingField.getText();
                            String genre = newGenreField.getText();
                            String actors = newActorsField.getText();
                            String director = newDirectorField.getText();
                            String producer = newProducerField.getText();

                            if (title.isEmpty() || launchDateString.isEmpty() || ratingText.isEmpty() || genre.isEmpty()
                                    || actors.isEmpty() || director.isEmpty() || producer.isEmpty()) {
                                JOptionPane.showMessageDialog(ModifyPanel.this, "Empty field/s.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            float rating;
                            try {
                                rating = Float.parseFloat(ratingText);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(ModifyPanel.this, "Invalid rating format.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            Date launchDate;
                            try {
                                launchDate = new SimpleDateFormat("dd/MM/yyyy").parse(launchDateString);
                            } catch (ParseException ex) {
                                JOptionPane.showMessageDialog(ModifyPanel.this, "Invalid date format. Please use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(movieRepository.modifyMovieByTitle(movieTitle, title, new java.sql.Date(launchDate.getTime()), rating)){
                                String[] actorsArray = actors.split(",\\s*");
                                for (String actor : actorsArray) {
                                    int index = actor.indexOf(" ");
                                    String firstName = actor.substring(0, index);
                                    String lastName = actor.substring(index + 1);
                                    if (!personRepository.exists(lastName, firstName)) {
                                        personRepository.insertIntoDatabase(lastName, firstName);
                                    }
                                    int personID = personRepository.getPersonID(lastName, firstName);
                                    movieRepository.modifyActorMovie(movieID, personID);
                                }

                                // Handle genre
                                if (!genreRepository.exists(genre)) {
                                    genreRepository.insertIntoDatabase(genre);
                                }
                                int genreID = genreRepository.getGenreID(genre);
                                movieRepository.modifyGenreMovie(movieID, genreID);

                                // Handle producer
                                if (!producerRepository.exists(producer)) {
                                    producerRepository.insertIntoDatabase(producer);
                                }
                                int producerID = producerRepository.getProducerID(producer);
                                movieRepository.modifyProducerMovie(movieID, producerID);

                                // Handle director
                                int index = director.indexOf(" ");
                                String directorFirstName = director.substring(0, index);
                                String directorLastName = director.substring(index + 1);
                                if (!personRepository.exists(directorLastName, directorFirstName)) {
                                    personRepository.insertIntoDatabase(directorLastName, directorFirstName);
                                }
                                int directorID = personRepository.getPersonID(directorLastName, directorFirstName);
                                movieRepository.modifyDirectorMovie(movieID, directorID);

                                JOptionPane.showMessageDialog(ModifyPanel.this, "Movie modified successfully.", "", JOptionPane.INFORMATION_MESSAGE);
                            }else{
                                JOptionPane.showMessageDialog(ModifyPanel.this, "Could not modify the movie.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                }
            }
        });
    }
}
