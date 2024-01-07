package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * provides methods to handle producers situations with database
 *
 * @author Simion Dragos Ionut
 */
public class ProducerRepository{
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    /**
     * retrieves a list of distinct producer names from the database
     *
     * @return a List of Strings containing producer names
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
     * inserts a new producer into the database
     *
     * @param name the name of the producer to be inserted
     * @return     the ID of the inserted producer, or -1 if the insertion fails
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
     * checks if a producer with the specified name already exists in the database
     *
     * @param name the name of the producer to check for existence
     * @return     true if the producer exists; false otherwise
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
     * gets the ID of a producer with the specified name
     *
     * @param name the name of the producer
     * @return     the ID of the producer, or -1 if not found
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
     * modifies the name of a producer in the database
     *
     * @param name    the current name of the producer
     * @param newName the new name for the producer
     * @return true if the modification is successful; false otherwise
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

