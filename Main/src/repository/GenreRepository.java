package repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
