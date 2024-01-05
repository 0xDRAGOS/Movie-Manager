package gui;

import repository.MovieRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePanel extends JPanel {
    private final MovieRepository movieRepository;
    private final JTextField movieTitleField;

    public DeletePanel(){
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        movieRepository = new MovieRepository();
        movieTitleField = new JTextField(15);
        JLabel movieTitleLabel = new JLabel("Enter title:");
        JButton deleteButton = new JButton("Delete");

        add(movieTitleLabel);
        add(movieTitleField);
        add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String movieTitle = movieTitleField.getText();
                int movieID = movieRepository.getMovieIDByTitle(movieTitle);
                if(movieID < 0){
                    JOptionPane.showMessageDialog(DeletePanel.this, "The movie was not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }else if(movieTitle.length() > 100){
                    JOptionPane.showMessageDialog(DeletePanel.this, "Title should be at max 100 characters.", "Error", JOptionPane.ERROR_MESSAGE);
                    movieTitleField.setText("");
                    return;
                }else if(movieRepository.deleteFromDatabase(movieTitle)){
                    JOptionPane.showMessageDialog(DeletePanel.this, "Movie deleted successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(DeletePanel.this, "Could not delete movie: " + movieTitle + ".", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
