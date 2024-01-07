package repository;

import entity.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * provides methods to handle movies situations with database
 *
 * @author Simion Dragos Ionut
 */
public class MovieRepository {
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    /**
     * loads the actors for a given movie ID from the database
     *
     * @param movieID the ID of the movie
     * @return        a string containing the names of actors for the movie
     */
    public String loadActors(int movieID) {
        String actors = "";
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT firstName, lastName FROM persons JOIN movie_actors ON movie_actors.person_id = persons.id WHERE movie_actors.movie_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, movieID);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            actors += resultSet.getString("firstName") + " " + resultSet.getString("lastName") + ", ";
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        actors = actors.substring(0, actors.length() - 2);
        return actors;
    }

    /**
     * loads the director for a given movie ID from the database
     *
     * @param movieID the ID of the movie
     * @return        a string containing the name of the director for the movie
     */
    public String loadDirector(int movieID) {
        String director = null;
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT firstName, lastName FROM persons JOIN movie_directors ON movie_directors.person_id = persons.id WHERE movie_directors.movie_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, movieID);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            director = resultSet.getString("firstName") + " " + resultSet.getString("lastName");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return director;
    }

    /**
     * loads the producer for a given movie ID from the database
     *
     * @param movieID the ID of the movie
     * @return        a string containing the name of the director for the movie
     */
    public String loadProducer(int movieID) {
        String producer = null;
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT name FROM production_companies JOIN movie_producers ON movie_producers.company_id = production_companies.id WHERE movie_producers.movie_id = ?;";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, movieID);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            producer = resultSet.getString("name");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producer;
    }

    /**
     * loads the genre for a given movie ID from the database
     *
     * @param movieID the ID of the movie
     * @return        a string containing the name of the director for the movie
     */
    public String loadGenre(int movieID) {
        String genre = null;
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT name FROM genres JOIN movie_genres ON movie_genres.genre_id = genres.id WHERE movie_genres.movie_id = ?;";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, movieID);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            genre = resultSet.getString("name");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genre;
    }

    /**
     * retrieves the movie ID for a given movie title from the database
     *
     * @param movieTitle the title of the movie
     * @return           the movie ID; -1 otherwise
     */
    public int getMovieIDByTitle(String movieTitle) {
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT id FROM movies WHERE title = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, movieTitle);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            return resultSet.getInt("id");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * retrieves a list of movie names from the database
     *
     * @return a List of Strings containing movie names
     */
    public List<String> getMoviesName() {
        List<String> moviesName = new ArrayList<>();
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT title FROM movies;";
                try (Statement statement = connection.createStatement()) {
                    try (ResultSet resultSet = statement.executeQuery(query)) {
                        while (resultSet.next()) {
                            moviesName.add(resultSet.getString("title"));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moviesName;
    }

    /**
     * retrieves a list of movies from the database
     *
     * @return a List of Movie objects containing movie details
     */
    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT * FROM movies;";
                try (Statement statement = connection.createStatement()) {
                    try (ResultSet resultSet = statement.executeQuery(query)) {
                        while (resultSet.next()) {
                            Movie movie = new Movie();
                            int movieID = resultSet.getInt("id");
                            movie.setTitle(resultSet.getString("title"));
                            movie.setLaunchDate(resultSet.getDate("launchDate"));
                            movie.setRating(resultSet.getFloat("rating"));
                            movie.setActors(loadActors(movieID));
                            movie.setDirector(loadDirector(movieID));
                            movie.setProducer(loadProducer(movieID));
                            movie.setGenre(loadGenre(movieID));

                            movies.add(movie);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }

    /**
     * retrieves a list of movies by the specified movie title from the database
     *
     * @param movieTitle the title of the movie
     * @return           a List of Movie objects containing details of the matching movies
     */
    public List<Movie> getMoviesByTitle(String movieTitle) {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT * FROM movies WHERE title = ?;";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, movieTitle);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            Movie movie = new Movie();
                            int movieID = resultSet.getInt("id");
                            movie.setTitle(resultSet.getString("title"));
                            movie.setLaunchDate(resultSet.getDate("launchDate"));
                            movie.setRating(resultSet.getFloat("rating"));
                            movie.setActors(loadActors(movieID));
                            movie.setDirector(loadDirector(movieID));
                            movie.setProducer(loadProducer(movieID));
                            movie.setGenre(loadGenre(movieID));

                            movies.add(movie);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * retrieves a single movie by the specified movie title from the database
     *
     * @param movieTitle the title of the movie
     * @return           a Movie object containing details of the matching movie, or null if not found
     */
    public Movie getMovieByTitle(String movieTitle) {
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT * FROM movies WHERE title = ?;";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, movieTitle);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            Movie movie = new Movie();
                            int movieID = resultSet.getInt("id");
                            movie.setTitle(resultSet.getString("title"));
                            movie.setLaunchDate(resultSet.getDate("launchDate"));
                            movie.setRating(resultSet.getFloat("rating"));
                            movie.setActors(loadActors(movieID));
                            movie.setDirector(loadDirector(movieID));
                            movie.setProducer(loadProducer(movieID));
                            movie.setGenre(loadGenre(movieID));

                            return movie;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if no movie is found
    }

    /**
     * retrieves a list of movies by the specified actor's first and last name from the database
     *
     * @param firstName the first name of the actor
     * @param lastName  the last name of the actor
     * @return          a List of Movie objects containing details of the movies featuring the specified actor
     */
    public List<Movie> getMoviesByActorName(String firstName, String lastName) {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT * FROM movies JOIN movie_actors ON movie_actors.movie_id = movies.id JOIN persons ON movie_actors.person_id = persons.id WHERE persons.firstName = ? AND persons.lastName = ?;";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, lastName);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            Movie movie = new Movie();
                            int movieID = resultSet.getInt("id");
                            movie.setTitle(resultSet.getString("title"));
                            movie.setLaunchDate(resultSet.getDate("launchDate"));
                            movie.setRating(resultSet.getFloat("rating"));
                            movie.setActors(loadActors(movieID));
                            movie.setDirector(loadDirector(movieID));
                            movie.setProducer(loadProducer(movieID));
                            movie.setGenre(loadGenre(movieID));

                            movies.add(movie);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * retrieves a list of movies by the specified director's first and last name from the database
     *
     * @param firstName the first name of the director
     * @param lastName  the last name of the director
     * @return          a List of Movie objects containing details of the movies directed by the specified person
     */
    public List<Movie> getMoviesByDirectorName(String firstName, String lastName) {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT * FROM movies JOIN movie_directors ON movie_directors.movie_id = movies.id JOIN persons ON movie_directors.person_id = persons.id WHERE persons.firstName = ? AND persons.lastName = ?;";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, lastName);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            Movie movie = new Movie();
                            int movieID = resultSet.getInt("id");
                            movie.setTitle(resultSet.getString("title"));
                            movie.setLaunchDate(resultSet.getDate("launchDate"));
                            movie.setRating(resultSet.getFloat("rating"));
                            movie.setActors(loadActors(movieID));
                            movie.setDirector(loadDirector(movieID));
                            movie.setProducer(loadProducer(movieID));
                            movie.setGenre(loadGenre(movieID));

                            movies.add(movie);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }

    /**
     * retrieves a list of movies by the specified genre from the database
     *
     * @param genreName yhe name of the genre
     * @return          a List of Movie objects containing details of the movies belonging to the specified genre
     */
    public List<Movie> getMoviesByGenre(String genreName) {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT * FROM movies JOIN movie_genres ON movie_genres.movie_id = movies.id JOIN genres ON movie_genres.genre_id = genres.id WHERE genres.name = ?;";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, genreName);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            Movie movie = new Movie();
                            int movieID = resultSet.getInt("id");
                            movie.setTitle(resultSet.getString("title"));
                            movie.setLaunchDate(resultSet.getDate("launchDate"));
                            movie.setRating(resultSet.getFloat("rating"));
                            movie.setActors(loadActors(movieID));
                            movie.setDirector(loadDirector(movieID));
                            movie.setProducer(loadProducer(movieID));
                            movie.setGenre(loadGenre(movieID));

                            movies.add(movie);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * retrieves a list of movies produced by the specified production company from the database
     *
     * @param producerName the name of the production company
     * @return             a List of Movie objects containing details of the movies produced by the specified company
     */
    public List<Movie> getMoviesByProducer(String producerName) {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT * FROM movies JOIN movie_producers ON movie_producers.movie_id = movies.id JOIN production_companies ON movie_producers.company_id = production_companies.id WHERE production_companies.name = ?;";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, producerName);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            Movie movie = new Movie();
                            int movieID = resultSet.getInt("id");
                            movie.setTitle(resultSet.getString("title"));
                            movie.setLaunchDate(resultSet.getDate("launchDate"));
                            movie.setRating(resultSet.getFloat("rating"));
                            movie.setActors(loadActors(movieID));
                            movie.setDirector(loadDirector(movieID));
                            movie.setProducer(loadProducer(movieID));
                            movie.setGenre(loadGenre(movieID));

                            movies.add(movie);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    /**
     * deletes a movie and its associated data from the database
     *
     * @param movieTitle the title of the movie to be deleted
     * @return true if the movie is successfully deleted; false otherwise
     */
    public boolean deleteFromDatabase(String movieTitle) {
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                try {
                    connection.setAutoCommit(false); // Start a transaction

                    int movieID = getMovieIDByTitle(movieTitle);

                    // Delete from movie_producers
                    String queryProducers = "DELETE FROM movie_producers WHERE movie_id = ?;";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(queryProducers)) {
                        preparedStatement.setInt(1, movieID);
                        preparedStatement.executeUpdate();
                        System.out.println("Deleted movie producer");
                    }

                    // Delete from movie_directors
                    String queryDirectors = "DELETE FROM movie_directors WHERE movie_id = ?;";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(queryDirectors)) {
                        preparedStatement.setInt(1, movieID);
                        preparedStatement.executeUpdate();
                        System.out.println("Deleted movie director");
                    }

                    // Delete from movie_actors
                    String queryActors = "DELETE FROM movie_actors WHERE movie_id = ?;";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(queryActors)) {
                        preparedStatement.setInt(1, movieID);
                        preparedStatement.executeUpdate();
                        System.out.println("Deleted movie actors");
                    }

                    // Delete from movie_genres
                    String queryGenres = "DELETE FROM movie_genres WHERE movie_id = ?;";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(queryGenres)) {
                        preparedStatement.setInt(1, movieID);
                        preparedStatement.executeUpdate();
                        System.out.println("Deleted movie genre");
                    }

                    // Delete from movies
                    String queryMovies = "DELETE FROM movies WHERE title = ?;";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(queryMovies)) {
                        preparedStatement.setString(1, movieTitle);
                        preparedStatement.executeUpdate();
                        System.out.println("Deleted movie");

                        // If everything is successful, commit the transaction
                        connection.commit();
                        return true;
                    }
                } catch (SQLException e) {
                    // If an error occurs, roll back the transaction
                    connection.rollback();
                    e.printStackTrace();
                    return false;
                } finally {
                    // Set auto commit back to true for subsequent operations
                    connection.setAutoCommit(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * inserts a new movie into the database
     *
     * @param title      the title of the movie
     * @param launchDate the launch date of the movie
     * @param rating     the rating of the movie
     * @return           the auto-generated ID of the inserted movie, or -1 if insertion fails
     */
    public int insertIntoDatabase(String title, Date launchDate, float rating) {
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "INSERT INTO movies (title, launchDate, rating) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, title);
                    preparedStatement.setDate(2, new java.sql.Date(launchDate.getTime()));
                    preparedStatement.setFloat(3, rating);;
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                            if (resultSet.next()) {
                                return resultSet.getInt(1);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * inserts a record into the database associating a movie with an actor
     *
     * @param movieID  the ID of the movie
     * @param personID the ID of the person (actor)
     * @return         true if the association is successfully inserted; false otherwise
     */
    public boolean insertMovieActorIntoDatabase(int movieID, int personID){
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "INSERT INTO movie_actors(movie_id, person_id) VALUES (?, ?)";
                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setInt(1, movieID);
                    preparedStatement.setInt(2, personID);
                    preparedStatement.executeUpdate();
                    return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * inserts a record into the database associating a movie with a genre
     *
     * @param movieID the ID of the movie
     * @param genreID the ID of the genre
     * @return        true if the association is successfully inserted; false otherwise
     */
    public boolean insertMovieGenreIntoDatabase(int movieID, int genreID){
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "INSERT INTO movie_genres(movie_id, genre_id) VALUES (?, ?)";
                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setInt(1, movieID);
                    preparedStatement.setInt(2, genreID);
                    preparedStatement.executeUpdate();
                    return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * inserts a record into the database associating a movie with a director
     *
     * @param movieID  the ID of the movie
     * @param personID the ID of the person (director)
     * @return         true if the association is successfully inserted; false otherwise
     */
    public boolean insertMovieDirectorIntoDatabase(int movieID, int personID){
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "INSERT INTO movie_directors(movie_id, person_id) VALUES (?, ?)";
                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setInt(1, movieID);
                    preparedStatement.setInt(2, personID);
                    preparedStatement.executeUpdate();
                    return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * inserts a record into the database associating a movie with a producer
     *
     * @param movieID    the ID of the movie
     * @param producerID the ID of the producer
     * @return           true if the association is successfully inserted; false otherwise
     */
    public boolean insertMovieProducerIntoDatabase(int movieID, int producerID){
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "INSERT INTO movie_producers(movie_id, company_id) VALUES (?, ?)";
                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setInt(1, movieID);
                    preparedStatement.setInt(2, producerID);
                    preparedStatement.executeUpdate();
                    return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * modifies the details of a movie in the database by title
     *
     * @param movieTitle         the title of the movie to be modified
     * @param newMovieTitle      the new title for the movie
     * @param newMovieLaunchDate the new launch date for the movie
     * @param newMovieRating     the new rating for the movie
     * @return                   true if the modification is successful; false otherwise
     */
    public boolean modifyMovieByTitle(String movieTitle, String newMovieTitle, Date newMovieLaunchDate, Float newMovieRating){
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "UPDATE movies SET title = ?, launchDate = ?, rating = ? WHERE title = ?";
                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setString(1, newMovieTitle);
                    preparedStatement.setDate(2, newMovieLaunchDate);
                    preparedStatement.setFloat(3, newMovieRating);
                    preparedStatement.setString(4, movieTitle);

                    int rowsAffected = preparedStatement.executeUpdate();
                    return rowsAffected > 0;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * modifies the association between a movie and an actor in the database
     *
     * @param movieID  the ID of the movie
     * @param personID the ID of the person (actor)
     * @return         true if the modification is successful; false otherwise
     */
    public boolean modifyActorMovie(int movieID, int personID){
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "UPDATE movie_actors SET person_id = ? WHERE movie_id = ?;";
                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setInt(1, personID);
                    preparedStatement.setInt(2, movieID);

                    int rowsAffected = preparedStatement.executeUpdate();
                    return rowsAffected > 0;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * modifies the association between a movie and a director in the database
     *
     * @param movieID  the ID of the movie
     * @param personID the ID of the person (director)
     * @return         true if the modification is successful; false otherwise
     */
    public boolean modifyDirectorMovie(int movieID, int personID){
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "UPDATE movie_directors SET person_id = ? WHERE movie_id = ?;";
                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setInt(1, personID);
                    preparedStatement.setInt(2, movieID);

                    int rowsAffected = preparedStatement.executeUpdate();
                    return rowsAffected > 0;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * modifies the association between a movie and a genre in the database
     *
     * @param movieID     the ID of the movie
     * @param newGenreID  the ID of the new genre
     * @return            true if the modification is successful; false otherwise
     */
    public boolean modifyGenreMovie(int movieID, int newGenreID){
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "UPDATE movie_genres SET genre_id = ? WHERE movie_id = ?;";
                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setInt(1, newGenreID);
                    preparedStatement.setInt(2, movieID);

                    int rowsAffected = preparedStatement.executeUpdate();
                    return rowsAffected > 0;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * modifies the association between a movie and a producer in the database
     *
     * @param movieID    the ID of the movie
     * @param producerID the ID of the producer
     * @return           true if the modification is successful; false otherwise
     */
    public boolean modifyProducerMovie(int movieID, int producerID){
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "UPDATE movie_producers SET company_id = ? WHERE movie_id = ?;";
                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setInt(1, producerID);
                    preparedStatement.setInt(2, movieID);

                    int rowsAffected = preparedStatement.executeUpdate();
                    return rowsAffected > 0;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
