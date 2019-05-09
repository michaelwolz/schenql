package de.unitrier.dbis.schenql.connection;

import java.sql.*;

public class DBConnection {
    private Connection conn = null;
    private Statement statement = null;
    private ResultSet rs = null;
    private ResultSetMetaData rsmd = null;

    public void executeQuery(String query) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String connectionURL = "jdbc:mysql://localhost/schenql-db?user=root&password=root";
            conn = DriverManager.getConnection(connectionURL);

            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(", ");
                    String columnVal = rs.getString(i);
                    System.out.print(rsmd.getColumnName(i) + ": " + columnVal);
                }
                System.out.println();
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
