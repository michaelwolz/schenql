package de.unitrier.dbis.schenql.connection;

import java.sql.*;

public class DBConnection {
    private Connection conn = null;
    private Statement statement = null;
    private ResultSet rs = null;

    public void executeQuery(String query) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String connectionURL = "jdbc:mysql://localhost/schenql-db?user=root&password=root&serverTimezone=UTC";
            conn = DriverManager.getConnection(connectionURL);

            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            if (!rs.next()) {
                System.out.println("Empty set.");
            } else {
                do {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(", ");
                        String columnVal = rs.getString(i);
                        System.out.print(rsmd.getColumnName(i) + ": " + columnVal);
                    }
                    System.out.println();
                } while (rs.next());
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
