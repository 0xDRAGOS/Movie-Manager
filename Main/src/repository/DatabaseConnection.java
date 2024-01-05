package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection{
    private final String url = "jdbc:postgresql://localhost:5432/movie_manager";
    private final String user = "postgres";
    private final String password = "1234";

    /**
     * establishes a connection to the database using the provided URL, username, and password
     *
     * @return a Connection object representing the database connection; returns null if the connection fails
     */
    public Connection connect(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, password);
            //System.out.println("Connected to db!");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
    }
}