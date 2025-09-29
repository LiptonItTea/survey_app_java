package org.liptonit.cli;

import org.liptonit.Vars;
import org.liptonit.db.repo.UserRepository;
import org.liptonit.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static class Entry {
        public String description;
        public Command command;

        public Entry(String description, Command command) {
            this.description = description;
            this.command = command;
        }
    }

    private final static Entry[] entries = {
            new Entry("Register user", scanner -> {
                System.out.println("To register user, you will need nickname, email and password");

                System.out.print("Nickname > ");
                String nickname = scanner.next();

                System.out.print("Email > ");
                String email = scanner.next();

                System.out.print("Password > ");
                String password = scanner.next();

                return UserRepository.getInstance(Vars.db).createEntity(new User(0, nickname, email, LocalDate.now(), password));
            }),

            new Entry("List all users", scanner -> {
                Iterable<User> all = UserRepository.getInstance(Vars.db).readEntities(u -> true);

                for (User u : all)
                    System.out.println(u);
                return true;
            })
    };
    public static void run() {
        for (int i = 0; i < entries.length; i++) {
            System.out.println((i + 1) + " " + entries[i].description);
        }
        System.out.println("-1 Exit");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");

            int index = 0;
            try {
                String data = scanner.next();
                index = Integer.parseInt(data);
            }
            catch (NumberFormatException e) {
//                System.err.println("Oops, wrong command!");
                continue;
            }

            if (index == -1) {
                System.out.println("Gracefully exiting...");
                return;
            }
            if (index > entries.length) {
                System.out.println("Index must be between 1 and " + entries.length);
                continue;
            }
            entries[index - 1].command.execute(scanner);
        }
    }
}
