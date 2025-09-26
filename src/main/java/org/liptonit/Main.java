package org.liptonit;


import org.liptonit.entity.User;
import org.liptonit.db.InMemoryDatabase;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        User u1 = new User(0, "john doe", "john@mail.ru", LocalDate.now(), "some hash");
        User u2 = new User(1, "john wick", "wick@mail.ru", LocalDate.now(), "some hash");
        User u3 = new User(2, "ana de armas", "ana@mail.ru", LocalDate.now(), "some hash");
        User u4 = new User(3, "ryan gosling", "ryan@mail.ru", LocalDate.now(), "some hash");
        User u5 = new User(0, "benedict kemberbetch", "sherlock@mail.ru", LocalDate.now(), "some hash");

        InMemoryDatabase db = new InMemoryDatabase();
        db.createEntity(User.class, u1);
        db.createEntity(User.class, u2);
        db.createEntity(User.class, u3);
        db.createEntity(User.class, u4);
        db.createEntity(User.class, u5);

        for (User u : db.readEntities(User.class, e -> true)) {
            System.out.println(u);
        }
    }
}