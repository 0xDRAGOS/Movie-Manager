package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProducerRepository{
    DatabaseConnection databaseConnection = new DatabaseConnection();
    public List<String> getProducers(){
        List<String> producers = new ArrayList<>();
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "SELECT name FROM production_companies;";
                try(Statement statement = connection.createStatement()){
                    try(ResultSet resultSet = statement.executeQuery(query)){
                        while(resultSet.next()){
                            producers.add(resultSet.getString("name"));
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return producers;
    }

    public int insertIntoDatabase(String name){
        try(Connection connection = databaseConnection.connect()){
            String query = "INSERT INTO production_companies (name) VALUES (?)";
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
                String query = "SELECT * FROM production_companies WHERE name = ?;";
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

    public int getProducerID(String name) {
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT id FROM production_companies WHERE name = ?;";
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
}

