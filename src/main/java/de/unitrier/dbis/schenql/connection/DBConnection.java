package de.unitrier.dbis.schenql.connection;

import java.sql.*;

public class DBConnection {
    private Connection conn = null;
    private Statement statement = null;
    private ResultSet rs = null;

    public void executeQuery(String query) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String connectionURL = "jdbc:mysql://localhost/schenql-db?user=root&password=root";
            conn = DriverManager.getConnection(connectionURL);

            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM `schenql-db.publications` LIMIT 10");
            while (rs.next()) {
                System.out.println(rs.getString("title"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private void close() {
        try {
            if (rs != null) {
                rs.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (conn != null) {
                conn.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
