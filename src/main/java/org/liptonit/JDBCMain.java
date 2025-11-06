package org.liptonit;

import org.liptonit.db.PostgreDatabase;
import org.liptonit.entity.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCMain {
    public static void main(String[] args) {
//        try {
//            String url = "jdbc:postgresql://localhost:5432/surveys";
//            String user = "postgres";
//            String password = "meow";
//            Connection connection = DriverManager.getConnection(url, user, password);
//
//            System.out.println(connection.getSchema());
//
//            PreparedStatement st = connection.prepareStatement("SELECT * FROM user_accs");
//            ResultSet rs = st.executeQuery();
//
//            while (rs.next()) {
//                System.out.println(rs.getLong(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getDate(4) + " " + rs.getString(5));
//            }
//
//        }
//        catch (SQLException e) {
//            System.out.println(e.getMessage());
//            throw new RuntimeException("Something wrong with db connection.");
//        }

        User u = new User(0, "java user", "java_user@mail.ru", null, "moew");
        System.out.println(Vars.userRepository.createEntity(u));
    }
}
