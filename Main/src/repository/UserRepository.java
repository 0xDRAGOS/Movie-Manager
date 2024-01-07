package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * provides a method to check validity of a username and password in database
 *
 * @author Simion Dragos Ionut
 */
public class UserRepository{
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    /**
     * checks the validity of a username and password combination in database
     *
     * @param username the username to be validated.
     * @param password the password to be validated.
     * @return         true if the username and password combination is valid; false otherwise
     */
    public boolean isValid(String username, String password) {
        Connection con = databaseConnection.connect();
        if (con != null) {
            String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
