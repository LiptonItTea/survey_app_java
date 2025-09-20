package org.liptonit;


import org.liptonit.entity.User;
import org.liptonit.db.InMemoryDatabase;
import org.liptonit.db.repo.UserRepository;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        User u1 = new User(0, "john doe", "john@mail.ru", LocalDate.now(), "some hash");
        User u2 = new User(1, "john wick", "wick@mail.ru", LocalDate.now(), "some hash");
        User u3 = new User(2, "ana de armas", "ana@mail.ru", LocalDate.now(), "some hash");
        User u4 = new User(3, "ryan gosling", "ryan@mail.ru", LocalDate.now(), "some hash");
        User u5 = new User(0, "benedict kemberbetch", "sherlock@mail.ru", LocalDate.now(), "some hash");

        InMemoryDatabase db = new InMemoryDatabase();
        UserRepository userRepo = db;

        userRepo.createEntity(u1);
        userRepo.createEntity(u2);
        userRepo.createEntity(u3);
        userRepo.createEntity(u4);
        userRepo.createEntity(u5);

        Iterable<User> all = userRepo.readEntities(u -> true);
        for (User u : all)
            System.out.println(u);
    }
}