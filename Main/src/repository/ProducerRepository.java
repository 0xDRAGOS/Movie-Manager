package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProducerRepository{
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    /**
     * @return a List of String representing producer names
     */
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

    /**
     * inserts a new production company/producer into the database
     *
     * @param name the name of the production company/producer to be inserted
     * @return the ID of the newly inserted production company/producer; returns -1 if insertion fails
     */
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

    /**
     * checks if a production company/producer with the given name exists in the database.
     *
     * @param name the name of the production company/producer to check for existence
     * @return true if the production company/producer exists in the database; false otherwise
     */
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

    /**
     * @param name the name of the production company/producer to retrieve the ID for
     * @return the ID of the production company/producer; returns -1 if not found
     */
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

    /**
     * modifies the name of a production company/producer in the database
     *
     * @param name    the current name of the production company/producer
     * @param newName the new name to set for the production company/producer
     * @return true if the modification was successful; false otherwise
     */
    public boolean modifyProducer(String name, String newName){
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "UPDATE production_companies SET name = ? WHERE name = ?;";
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

