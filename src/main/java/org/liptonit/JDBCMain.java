package org.liptonit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCMain {
    public static void main(String[] args) {
        try {
            String url = "jdbc:postgresql://localhost:5432/surveys";
            String user = "postgres";
            String password = "";
            Connection connection = DriverManager.getConnection(url, user, password);

            System.out.println(connection.getSchema());
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Something wrong with db connection.");
        }

        
    }
}
