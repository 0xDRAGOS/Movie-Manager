package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository{
    private final DatabaseConnection dbCon = new DatabaseConnection();

    /**
     * validates a user by checking if the provided username and password match any records in the database
     *
     * @param username the username of the user to validate
     * @param password the password of the user to validate
     * @return true if the provided username and password match a record in the database; false otherwise
     */
    public boolean isValid(String username, String password) {
        Connection con = dbCon.connect();
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
