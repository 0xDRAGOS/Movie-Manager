package app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

public class MovieManager {
    private List<Movie> movieCollection = new ArrayList<>();
    public void addMovie(Movie movie){
        movieCollection.add(movie);
    }
    public void addAll(List<Movie> movies){
        movieCollection.addAll(movies);
    }
    public void removeMovie(Movie movie){
        movieCollection.remove(movie);
    }
    public boolean createRaport(String fileName){
        try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))){
            List<Movie> raport = movieCollection.stream()
                    .sorted(Comparator.comparing(Movie::getGenre)
                        .thenComparing(Movie::getTitle)
                            .thenComparing(Movie::getRating))
                            .collect(Collectors.toList());
        for(Movie movie : raport){
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

