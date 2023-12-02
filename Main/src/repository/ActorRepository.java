package repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ActorRepository {
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    public List<String> getActors(){
        List<String> actors = new ArrayList<>();

        try(Connection connection = databaseConnection.connect()){
            if(connection != null){
                String query = "SELECT DISTINCT firstName, lastName from persons JOIN movie_actors ON persons.id = movie_actors.person_id;";
                try(Statement statement = connection.createStatement()){
                    try (ResultSet resultSet = statement.executeQuery(query)){
                        while (resultSet.next()){
                            actors.add(resultSet.getString(1) + " " + resultSet.getString(2));
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return actors;
    }
}
