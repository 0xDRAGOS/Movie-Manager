package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRepository {
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    /**
     * inserts a new person into the database
     *
     * @param lastName  the last name of the person to be inserted
     * @param firstName the first name of the person to be inserted
     * @return the ID of the newly inserted person; returns -1 if insertion fails
     */
    public int insertIntoDatabase(String lastName, String firstName){
        try(Connection connection = databaseConnection.connect()){
            String query = "INSERT INTO persons (firstName, lastName) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
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
     * checks if a person with the given first and last name exists in the database
     *
     * @param lastName  the last name of the person to check for existence
     * @param firstName the first name of the person to check for existence
     * @return true if the person exists in the database; false otherwise
     */
    public boolean exists(String lastName, String firstName){
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "SELECT * FROM persons WHERE firstName = ? AND lastName = ?;";
                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, lastName);
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
     * @param lastName  the last name of the person to retrieve the ID for
     * @param firstName the first name of the person to retrieve the ID for
     * @return the ID of the person; returns -1 if the person is not found
     */
    public int getPersonID(String lastName, String firstName) {
        try (Connection connection = databaseConnection.connect()) {
            if (connection != null) {
                String query = "SELECT id FROM persons WHERE firstName = ? AND lastName = ?;";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, lastName);
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
     * modifies the first and last names of a person in the database
     *
     * @param lastName     the current last name of the person
     * @param firstName    the current first name of the person
     * @param newLastName  the new last name to set for the person
     * @param newFirstName the new first name to set for the person
     * @return true if the modification was successful; false otherwise
     */
    public boolean modifyPerson(String lastName, String firstName, String newLastName, String newFirstName){
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "UPDATE production_companies SET lastName = ?, firstName = ? WHERE lastName = ? AND firstName = ?;";
                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setString(1, newLastName);
                    preparedStatement.setString(2, newFirstName);
                    preparedStatement.setString(3, lastName);
                    preparedStatement.setString(4, firstName);

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
