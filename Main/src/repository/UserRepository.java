package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository{
    private DatabaseConnection dbCon = new DatabaseConnection();
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
