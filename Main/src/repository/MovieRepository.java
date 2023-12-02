package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private DatabaseConnection dbCon = new DatabaseConnection();

    public int getMovieIDByTitle(String title){
        int id = -1;
        try(Connection connection = dbCon.connect()){
            if(connection != null){
                String query = "SELECT id FROM movies WHERE title = " + title;
                try(Statement statement = connection.createStatement()){
                    try(ResultSet resultSet = statement.executeQuery(query)){
                        if(resultSet.next()){
                            id = resultSet.getInt(1);
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }
    public List<String> loadActors(int movieID){
        List<String> actors = new ArrayList<>();

        try(Connection connection = dbCon.connect()){
            if(connection != null){
                String query = "SELECT firstName, lastName FROM persons JOIN movie_actors ON movie_actors.person_id = persons.id WHERE movie_actors.movie_id = ?";
                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setInt(1, movieID);
                    try(ResultSet resultSet = preparedStatement.executeQuery()){
                        while(resultSet.next()){
                            System.out.println("added actor to list");
                            String actorName = resultSet.getString("firstName") + " " + resultSet.getString("lastName");
                            actors.add(actorName);
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return actors;
    }

    public static void main(String[] args) {
        MovieRepository movieRepository = new MovieRepository();
        List<String> a = new ArrayList<>(movieRepository.loadActors(1));
        for(String item : a ){
            System.out.println(item);
        }
    }
}

