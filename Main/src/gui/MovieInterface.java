package gui;

import app.Movie;
import repository.MovieRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MovieInterface extends Component implements Displayable{
    private final MovieRepository movieRepository = new MovieRepository();
    JButton playButton;
    JTextArea textArea;

    /**
     * displays movie information in the provided JTextArea based on a given title
     *
     * @param textArea the JTextArea where movie information will be displayed
     * @param stringCondition the title used to filter and display movie information
     */
    public void display(JTextArea textArea, String stringCondition){
        textArea.setText("");
        textArea.setEditable(false);
        List<Movie> movies = movieRepository.getMoviesByTitle(stringCondition);
        for (Movie item : movies) {
            textArea.append(item.toStringLine());
            }
    }

    /**
     * displays movie information along with a 'Play Movie' button in the provided JPanel based on a given title
     *
     * @param jPanel the JPanel where movie information and 'Play Movie' button will be displayed
     * @param stringCondition the title used to filter and display movie information
     */
    public void displayButton(JPanel jPanel, String stringCondition){
        textArea = new JTextArea();
        textArea.setText("");
        textArea.setEditable(false);
        List<Movie> movies = movieRepository.getMoviesByTitle(stringCondition);
        for (Movie item : movies) {
            textArea.append(item.toStringLine());
        }
        playButton = new JButton("Play Movie");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playMovie(stringCondition);
            }
        });

        jPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel.add(new JScrollPane(textArea));
        jPanel.add(playButton);
    }

    /**
     * open the movie file corresponding to the given movie title.
     *
     * @param movieTitle the title of the movie whose file will be opened
     */
    private void playMovie(String movieTitle){
        String path = "Main/src/movies/" + movieTitle + ".mp4";
        File movieFile = new File(path);

        if(movieFile.exists()){
            try{
                Desktop.getDesktop().open(movieFile);
            }catch(IOException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening video file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Video file does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
