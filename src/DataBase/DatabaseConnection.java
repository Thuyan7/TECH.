package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.cj.jdbc.Driver;

public class DatabaseConnection {
   public static Connection getConnection() {
		Connection c = null;
		try {
			DriverManager.registerDriver(new Driver());
			String url ="jdbc:mySQL://localhost:3306/tech";
			String username="root";
			String password="ăâêồ";
			c=DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
		
	}
	public static void closeConnection(Connection c) {
		try {
			if(c!=null) {
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
