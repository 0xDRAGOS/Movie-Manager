package repository;

import app.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private DatabaseConnection databaseConnection = new DatabaseConnection();

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
        actors.substring(0, actors.length() - 2);
        return actors;
    }

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

    public int getMovieIDByTitle(String title) {
        int id = -1;
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT id FROM movies WHERE title = " + title;
                try (Statement statement = connection.createStatement()) {
                    try (ResultSet resultSet = statement.executeQuery(query)) {
                        if (resultSet.next()) {
                            id = resultSet.getInt(1);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

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

    public List<Movie> getMovieByTitle(String movieTitle) {
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

}
