package org.liptonit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    private static Connection connection;
//    private static final String url = "jdbc:postgresql://localhost:5432/surveys";
    private static final String url = "jdbc:h2:~/test";
//    private static final String user = "postgres";
private static final String user = "sa";
//    private static final String password = "meow";
private static final String password = "";

    public static Connection getConnection() {
        try {
            Class.forName ("org.h2.Driver");
            if (connection == null)
                connection = DriverManager.getConnection(url, user, password);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return connection;
    }
}
