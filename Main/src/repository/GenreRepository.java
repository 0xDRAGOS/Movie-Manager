package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * provides methods to handle genres situations with database
 *
 * @author Simion Dragos Ionut
 */
public class GenreRepository {
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    /**
     * retrieves a list of distinct genres from the database
     *
     * @return a List of Strings containing actor names
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
     * @return     the ID of the inserted genre; -1 otherwise
     */
    public int insertIntoDatabase(String name){
        try(Connection connection = databaseConnection.connect()){
            String query = "INSERT INTO genres (name) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();
                try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                    if(resultSet.next()){
                        return resultSet.getInt(1);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * checks if a genre with the specified name already exists in the database
     *
     * @param name the name of the genre to check for existence
     * @return     true if the genre exists; false otherwise
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
     * gets the ID of a genre with the specified name
     *
     * @param name the name of the genre
     * @return     the ID of the genre; -1 otherwise
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
     * @param newName the new name for the genre
     * @return        true if the modification is successful; false otherwise
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
