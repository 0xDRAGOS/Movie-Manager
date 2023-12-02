package app;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Movie {
    private String name;
    private String category;
    private Date launchDate;
    private float rating;
    private String actors;
    private String director;
    private String producer;

    public Movie(){}
    public Movie(String name, String category, Date launchDate, float rating, String actors, String director, String producer) {
        this.name = name;
        this.category = category;
        this.launchDate = launchDate;
        this.rating = rating;
        this.actors = actors;
        this.director = director;
        this.producer = producer;
    }

    public String toString(){
        return String.format("Nume: %s, Categorie: %s, Data Lansare: %s, Rating: %f, Actors: %s, Director: %s, Producer: %s",
                name, category, launchDate.toString(), rating, actors, director, producer);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        name = console.nextLine();
        System.out.println("Category: ");
        category = console.nextLine();
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
        System.out.println("Name: " + name);
        System.out.println("Category: " + category);
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
