package repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DirectorRepository {
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    /**
     * @return a List of String representing the full names of distinct directors
     */
    public List<String> getDirectors(){
        List<String> directors = new ArrayList<>();
        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "SELECT DISTINCT firstName, lastName from persons JOIN movie_directors ON persons.id = movie_directors.person_id;";
                try(Statement statement = connection.createStatement()){
                    try (ResultSet resultSet = statement.executeQuery(query)){
                        while (resultSet.next()){
                            directors.add(resultSet.getString(1) + " " + resultSet.getString(2));
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return directors;
    }
}
