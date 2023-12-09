package app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Movie {
    private final int MAX_MOVIE_TITLE = 100;
    private String title;
    private String genre;
    private Date launchDate;
    private float rating;
    private String actors;
    private String director;
    private String producer;

    public Movie(){}
    public Movie(String title, String genre, Date launchDate, float rating, String actors, String director, String producer) {
        this.title = title;
        this.genre = genre;
        this.launchDate = launchDate;
        this.rating = rating;
        this.actors = actors;
        this.director = director;
        this.producer = producer;
    }

    public String toString(){
        return String.format("Name: %s, Category: %s, Launch date: %s, Rating: %f, Actors: %s, Director: %s, Producer: %s\n",
                title, genre, launchDate.toString(), rating, actors, director, producer);
    }

    public String toStringLine(){
        return String.format("Name: %s \nCategory: %s \nLaunch date: %s \nRating: %f \nActors: %s \nDirector: %s \nProducer: %s\n",
                title, genre, launchDate.toString(), rating, actors, director, producer);
    }

    public String[] toStringTable(){
        return new String[]{title, genre, launchDate.toString(), String.valueOf(rating), actors, director, producer};
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void readMovie(){
        Date launchDate;

        Scanner console = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("Name: ");
        title = console.nextLine();
        System.out.println("Category: ");
        genre = console.nextLine();
        System.out.println("Actors: ");
        actors = console.nextLine();
        System.out.println("Director: ");
        director = console.nextLine();
        System.out.println("Producer: ");
        producer = console.nextLine();
        System.out.println("Rating: ");
        rating = console.nextFloat();

        while (true) {
            System.out.println("Launch Date (YYYY-MM-DD): ");
            String dateString = console.next();
            try {
                launchDate = dateFormat.parse(dateString.trim());
                break; // Exit the loop if parsing is successful
            } catch (ParseException e) {
                System.out.println("Formatul datei este incorect. Utilizati formatul YYYY-MM-DD.");
            }
        }
        setLaunchDate(launchDate);
    }

    public void displayMovie(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("Movie Details:");
        System.out.println("Name: " + title);
        System.out.println("Genre: " + genre);
        System.out.println("Launch Date: " + dateFormat.format(launchDate));
        System.out.println("Rating: " + rating);
        System.out.println("Actors: " + actors);
        System.out.println("Director: " + director);
        System.out.println("Producer: " + producer);
    }
//
//    public static void main(String[] args) {
//        Movie movie = new Movie();
//        movie.readMovie();
//        movie.displayMovie();
//    }
}
