package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreRepository {
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    /**
     * @return a List of String representing genres
     */
    public List<String> getGenres(){
        List<String> genres = new ArrayList<>();

        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "SELECT name FROM genres;";
                try(Statement statement = connection.createStatement()){
                    try(ResultSet resultSet = statement.executeQuery(query)){
                        while (resultSet.next()){
                            genres.add(resultSet.getString("name"));
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return genres;
    }

    /**
     * inserts a new genre into the database
     *
     * @param name the name of the genre to be inserted
     */
    public void insertIntoDatabase(String name){
        try(Connection connection = databaseConnection.connect()){
            String query = "INSERT INTO genres (name) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();
                try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                    if(resultSet.next()){
                        resultSet.getInt(1);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * checks if a genre with the given name exists in the database
     *
     * @param name the name of the genre to check for existence
     * @return true if the genre exists in the database; false otherwise
     */
    public boolean exists(String name){
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "SELECT * FROM genres WHERE name = ?;";
                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setString(1, name);
                    try(ResultSet resultSet = preparedStatement.executeQuery()){
                        if(resultSet.next()){
                            return resultSet.getInt(1) > 0;
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param name the name of the genre to retrieve the ID for
     * @return the ID of the genre; returns -1 if the genre is not found
     */
    public int getGenreID(String name) {
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT id FROM genres WHERE name = ?;";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, name);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            return resultSet.getInt(1);
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
     * modifies the name of a genre in the database
     *
     * @param name    the current name of the genre
     * @param newName the new name to set for the genre
     * @return true if the modification was successful; false otherwise
     */
    public boolean modifyGenre(String name, String newName){
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "UPDATE genres SET name = ? WHERE name = ?;";
                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setString(1, newName);
                    preparedStatement.setString(2, name);

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
