import java.sql.*;

class DatabaseConnection {

    private Connection conn;

    DatabaseConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptiddb", "tester", "tester");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Connection getConn() {
        return conn;
    }
}
