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
    public void removeMovie(Movie movie){
        movieCollection.remove(movie);
    }
    public void createRaport(String fileName){
        try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))){
            List<Movie> raport = movieCollection.stream()
                    .sorted(Comparator.comparing(Movie::getGenre)
                        .thenComparing(Movie::getTitle)
                            .thenComparing(Movie::getRating))
                            .collect(Collectors.toList());
        for(Movie movie : raport){
            writer.println(movie.toString());
        }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MovieManager manager = new MovieManager();

        manager.addMovie(new Movie("Film1", "Actiune", new Date(2022, 1, 1), 9, "ASDASDASD", "asd", "Asd"));
        manager.addMovie(new Movie("Film2", "Comedie", new Date(2022, 1, 1), 3, "ASDASDASD", "asd", "Asd"));
        manager.addMovie(new Movie("Film3", "Actiune", new Date(2022, 1, 1), 4, "ASDASDASD", "asd", "Asd"));
        manager.addMovie(new Movie("Film4", "Comedie", new Date(2022, 1, 1), 2, "ASDASDASD", "asd", "Asd"));

        manager.createRaport("movie_raport.txt");
    }
}

