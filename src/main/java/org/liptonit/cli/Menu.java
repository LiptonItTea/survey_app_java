package org.liptonit.cli;

import org.liptonit.Vars;
import org.liptonit.db.repo.AnswerRepository;
import org.liptonit.db.repo.QuestionRepository;
import org.liptonit.db.repo.SurveyRepository;
import org.liptonit.db.repo.UserRepository;
import org.liptonit.entity.Answer;
import org.liptonit.entity.Question;
import org.liptonit.entity.Survey;
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

                UserRepository.getInstance(Vars.db).createEntity(new User(0, nickname, email, LocalDate.now(), password));
                return true;
            }),

            new Entry("Edit user", scanner -> {
                System.out.print("User nickname\n> ");
                String nickname = scanner.next();

                System.out.println("User password\n> ");
                String password = scanner.next();

                Iterable<User> all = UserRepository.getInstance(Vars.db).readEntities(
                        u -> u.getNickname().equals(nickname) &&
                                    u.getHashedPassword().equals(password));
                User u = all.iterator().next();

                if (u == null) {
                    System.out.println("No such user found.");
                    return false;
                }

                System.out.print("New nickname, - if same\n> ");
                String newNickname = scanner.next();

                System.out.print("New email, - if same\n> ");
                String email = scanner.next();

                System.out.print("New password, - if same\n> ");
                String newPassword = scanner.next();

                UserRepository.getInstance(Vars.db).updateEntityById(u.getId(), new User(
                        u.getId(),
                        newNickname.equals("-") ? nickname : newNickname,
                        email.equals("-") ? u.getEmail() : email,
                        u.getRegistrationDate(),
                        newPassword.equals("-") ? password : newPassword
                ));
                return true;
            }),

            new Entry("Delete user", scanner -> {
                System.out.print("User nickname\n> ");
                String nickname = scanner.next();

                System.out.println("User password\n> ");
                String password = scanner.next();

                Iterable<User> all = UserRepository.getInstance(Vars.db).readEntities(
                        u -> u.getNickname().equals(nickname) &&
                                u.getHashedPassword().equals(password));
                User u = all.iterator().next();

                if (u == null) {
                    System.out.println("No such user found.");
                    return false;
                }

                UserRepository.getInstance(Vars.db).deleteEntityById(u.getId());
                return true;
            }),

            new Entry("Create survey", scanner -> {
                System.out.print("Survey name\n> ");
                String name = scanner.next();

                System.out.print("Survey description\n> ");
                String descr = scanner.next();

                System.out.print("ID user creator\n> ");
                long idUserCreator = scanner.nextLong();

                long surveyId = SurveyRepository.getInstance(Vars.db).createEntity(new Survey(
                        0, name, descr, idUserCreator
                )).getId();

                while (true) {
                    System.out.print("Question text, - if no question\n> ");
                    String qText = scanner.next();

                    if (qText.equals("-"))
                        break;

                    System.out.print("Multiple answers\n> ");
                    boolean multipleAnswers = scanner.nextBoolean();

                    long questionId = QuestionRepository.getInstance(Vars.db).createEntity(new Question(
                            0, qText, multipleAnswers, surveyId
                    )).getId();

                    while (true) {
                        System.out.println("Answer text, - if no answer\n> ");
                        String aText = scanner.next();

                        if (aText.equals("-"))
                            break;

                        AnswerRepository.getInstance(Vars.db).createEntity(new Answer(
                                0, aText, questionId
                        ));
                    }
                }

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
