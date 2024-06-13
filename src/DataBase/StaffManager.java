package DataBase;

import java.sql.SQLException;
import java.sql.PreparedStatement;

import database.DatabaseConnection;

public class StaffManager {
    public static boolean updateApprovalStatus(int accountId, boolean isApproved) {
        String sql = "UPDATE accountuser SET is_approved = ? WHERE id = ?";
        try (
            java.sql.Connection con = DatabaseConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
        ) {
            stmt.setBoolean(1, isApproved);
            stmt.setInt(2, accountId);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
