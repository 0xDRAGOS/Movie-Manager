package repository;

import app.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    /**
     * loads the actors for a given movie ID
     *
     * @param movieID The ID of the movie to retrieve actors for
     * @return A string containing the names of the actors in the movie
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
     * loads the director for a given movie ID
     *
     * @param movieID The ID of the movie to retrieve the director for
     * @return A string containing the name of the director
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
     * loads the producer for a given movie ID
     *
     * @param movieID The ID of the movie to retrieve the producer for
     * @return A string containing the name of the producer
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
     * loads the genre for a given movie ID
     *
     * @param movieID The ID of the movie to retrieve the genre for
     * @return A string containing the genre of the movie
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
     * retrieves the movie ID based on its title
     *
     * @param movieTitle The title of the movie
     * @return The ID of the movie, or -1 if not found
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
     * @return String List of movie titles
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
     * @return Movie List of all movies from the database
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
     * @param movieTitle the title of the movie to search for
     * @return Movie List matching the given title, or null if not found
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
     * @param movieTitle the title of the movie to search for
     * @return Movie object matching the given title, or null if not found
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
        return null;
    }

    /**
     * @param firstName The first name of the actor
     * @param lastName  The last name of the actor
     * @return Movie List featuring the specified actor
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
     * @param firstName The first name of the actor
     * @param lastName  The last name of the actor
     * @return Movie List featuring the specified director
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
     * @param genreName the name of the genre to search for movies
     * @return Movie List featuring the specified actor
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
     * @param producerName the name of the producer to search for movies
     * @return Movie List featuring the specified actor
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
     * deletes a movie from the database based on its title
     *
     * @param movieTitle the title of the movie to be deleted
     * @return true if the movie was successfully deleted, otherwise false
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
     * inserts a new movie entry into the database
     *
     * @param title      the title of the new movie
     * @param launchDate the launch date of the new movie
     * @param rating     the rating of the new movie
     * @return the ID of the inserted movie or -1 if insertion fails
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
     * inserts a record into the database indicating a new actor for a specific movie
     *
     * @param movieID  the ID of the movie associated with the new actor
     * @param personID the ID of the actor to be added to the movie
     * @return true if the actor was successfully inserted for the movie; otherwise false
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
     * inserts a record into the database indicating a new genre for a specific movie
     *
     * @param movieID the ID of the movie associated with the new genre
     * @param genreID the ID of the genre to be added to the movie
     * @return true if the genre was successfully inserted for the movie; otherwise false
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
     * inserts a record into the database indicating a new director for a specific movie
     *
     * @param movieID  the ID of the movie associated with the new director
     * @param personID the ID of the director to be added to the movie
     * @return true if the director was successfully inserted for the movie; otherwise false
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
     * inserts a record into the database indicating a new producer for a specific movie
     *
     * @param movieID    the ID of the movie associated with the new producer
     * @param producerID the ID of the producer to be added to the movie
     * @return true if the producer was successfully inserted for the movie; otherwise false
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
     * modifies a movie's details based on its title
     *
     * @param movieTitle       the title of the movie to be modified
     * @param newMovieTitle    the updated title for the movie
     * @param newMovieLaunchDate the updated launch date for the movie
     * @param newMovieRating   the updated rating for the movie
     * @return true if the movie details were successfully modified; otherwise false
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
     * modifies an actor associated with a specific movie
     *
     * @param movieID  the ID of the movie to which the actor is linked
     * @param personID the ID of the new actor to be associated with the movie
     * @return true if the modification was successful; otherwise false
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
     * modifies a director associated with a specific movie
     *
     * @param movieID  the ID of the movie to which the director is linked
     * @param personID the ID of the new director to be associated with the movie
     * @return true if the modification was successful; otherwise false
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
     * modifies the genre of a specific movie
     *
     * @param movieID    the ID of the movie whose genre is to be updated
     * @param newGenreID the ID of the new genre to associate with the movie
     * @return true if the modification was successful; otherwise false
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
     * modifies the producer associated with a specific movie
     *
     * @param movieID    the ID of the movie to which the producer is linked
     * @param producerID the ID of the new producer to be associated with the movie
     * @return true if the modification was successful; otherwise false
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