package utility;

import entity.Movie;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * provides a method to create a movie report
 *
 * @author Simion Dragos Ionut
 */
public class ReportCreator {
    private final List<Movie> movieCollection = new ArrayList<>();
    public void addMovie(Movie movie){
        movieCollection.add(movie);
    }
    public void addAll(List<Movie> movies){
        movieCollection.addAll(movies);
    }
    public void removeMovie(Movie movie){
        movieCollection.remove(movie);
    }

    /**
     * creates a report based on the movie collection and writes it to the specified file
     *
     * @param fileName The name of the file where the report will be written
     * @return true if the report is successfully created and written; false otherwise
     */
    public boolean createReport(String fileName){
        try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))){
            List<Movie> report = movieCollection.stream()
                    .sorted(Comparator.comparing(Movie::getGenre)
                        .thenComparing(Movie::getTitle)
                            .thenComparing(Movie::getRating))
                            .toList();
        for(Movie movie : report){
            writer.println(movie.toString());
            writer.println("");
        }
        writer.close();
        return true;
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("Error creating file: " + e.getMessage());
            return false;
        }
    }
}

