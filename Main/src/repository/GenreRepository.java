package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreRepository {
    private DatabaseConnection databaseConnection = new DatabaseConnection();
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
