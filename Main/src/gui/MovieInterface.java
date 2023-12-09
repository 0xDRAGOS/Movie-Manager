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
    private MovieRepository movieRepository = new MovieRepository();
    JButton playButton;
    JTextArea textArea;
    public void display(JTextArea textArea, String stringCondition){
        textArea.setText("");
        textArea.setEditable(false);
        List<Movie> movies = movieRepository.getMoviesByTitle(stringCondition);
        for (Movie item : movies) {
            textArea.append(item.toStringLine());
            }
    }
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

    private void playMovie(String movieTitle){
        String path = "Main/src/movies/" + movieTitle + ".mp4";
        System.out.println(path);
        File movieFile = new File(path);
        System.out.println("File path: " + movieFile.getAbsolutePath());
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
