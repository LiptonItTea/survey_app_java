package org.liptonit.cli;

import org.liptonit.Vars;
import org.liptonit.entity.*;

import java.time.LocalDate;
import java.util.*;

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
                String nickname = scanner.nextLine();

                System.out.print("Email > ");
                String email = scanner.nextLine();

                System.out.print("Password > ");
                String password = scanner.nextLine();

                Vars.userRepository.createEntity(new User(0, nickname, email, LocalDate.now(), password));
                return true;
            }),

            new Entry("Edit user", scanner -> {
                System.out.print("User nickname\n> ");
                String nickname = scanner.nextLine();

                System.out.println("User password\n> ");
                String password = scanner.nextLine();

                List<User> all = Vars.userRepository.readEntities(
                        u -> u.getNickname().equals(nickname) &&
                                    u.getHashedPassword().equals(password));
                User u = all.getFirst();

                if (u == null) {
                    System.out.println("No such user found.");
                    return false;
                }

                System.out.print("New nickname, - if same\n> ");
                String newNickname = scanner.nextLine();

                System.out.print("New email, - if same\n> ");
                String email = scanner.nextLine();

                System.out.print("New password, - if same\n> ");
                String newPassword = scanner.nextLine();

                Vars.userRepository.updateEntityById(u.getId(), patched -> {
                    patched.setNickname(newNickname.equals("-") ? nickname : newNickname);
                    patched.setEmail(email.equals("-") ? u.getEmail() : email);
                    patched.setHashedPassword(newPassword.equals("-") ? password : newPassword);
                });
                return true;
            }),

            new Entry("Delete user", scanner -> {
                System.out.print("User nickname\n> ");
                String nickname = scanner.nextLine();

                System.out.println("User password\n> ");
                String password = scanner.nextLine();

                List<User> all = Vars.userRepository.readEntities(
                        u -> u.getNickname().equals(nickname) &&
                                u.getHashedPassword().equals(password));
                User u = all.getFirst();

                if (u == null) {
                    System.out.println("No such user found.");
                    return false;
                }

                Vars.userRepository.deleteEntityById(u.getId());
                return true;
            }),

            new Entry("List all users", scanner -> {
                for (User u : Vars.userRepository.readEntities(u -> true))
                    System.out.println(u);
                return true;
            }),

            new Entry("Create survey", scanner -> {
                System.out.print("Survey name\n> ");
                String name = scanner.nextLine();

                System.out.print("Survey description\n> ");
                String descr = scanner.nextLine();

                System.out.print("ID user creator\n> ");
                long idUserCreator = Long.parseLong(scanner.nextLine());

                long surveyId = Vars.surveyRepository.createEntity(new Survey(
                        0, name, descr, idUserCreator
                )).getId();

                while (true) {
                    System.out.print("Question text, - if no question\n> ");
                    String qText = scanner.nextLine();

                    if (qText.equals("-"))
                        break;

                    System.out.print("Multiple answers\n> ");
                    boolean multipleAnswers = Boolean.parseBoolean(scanner.nextLine());

                    long questionId = Vars.questionRepository.createEntity(new Question(
                            0, qText, multipleAnswers, surveyId
                    )).getId();

                    while (true) {
                        System.out.print("Answer text, - if no answer\n> ");
                        String aText = scanner.nextLine();

                        if (aText.equals("-"))
                            break;

                        Vars.answerRepository.createEntity(new Answer(
                                0, aText, questionId
                        ));
                    }
                }

                return true;
            }),

            new Entry("List all surveys", scanner -> {
                for (Survey s : Vars.surveyRepository.readEntities(s -> true))
                    System.out.println(s);

                return true;
            }),

            new Entry("Conduct survey", scanner -> {
                System.out.print("Survey ID\n> ");
                long surveyId = Long.parseLong(scanner.nextLine());

                Survey survey = Vars.surveyRepository.readEntityById(surveyId);
                if (survey == null) {
                    System.out.println("Can't find any survey with given ID.");
                    return false;
                }

                System.out.print("User ID\n> ");
                long userId = Long.parseLong(scanner.nextLine());
                //todo check user

                long completedSurveyId = Vars.completedSurveyRepository.createEntity(new CompletedSurvey(
                        0, userId
                )).getId();

                List<Question> questions = Vars.questionRepository.readEntities(q -> q.getIdSurvey() == surveyId);
                for (Question q : questions) {
                    List<Answer> answers = Vars.answerRepository.readEntities(a -> a.getIdQuestion() == q.getId());

                    System.out.println(q.getText());
                    for (int i = 0; i < answers.size(); i++)
                        System.out.println((i + 1) + ". " + answers.get(i).getText());

                    System.out.print("> ");
                    int variant = Integer.parseInt(scanner.nextLine());
                    long answerId = answers.get(variant - 1).getId();
                    Vars.questionAnswerRepository.createEntity(new QuestionAnswer(
                            0, completedSurveyId, answerId
                    ));
                }
                return true;
            }),

            new Entry("Get survey statistics", scanner -> {
                System.out.print("Survey ID\n> ");
                long surveyId = Long.parseLong(scanner.nextLine());

                List<Question> questions = Vars.questionRepository.readEntities(q -> q.getIdSurvey() == surveyId);
                Map<Long, List<Answer>> answersPerQuestion = new HashMap<>();

                List<Answer> answers = new ArrayList<>();
                for (Question q : questions) {
                    List<Answer> temp = Vars.answerRepository.readEntities(a -> a.getIdQuestion() == q.getId());
                    answersPerQuestion.put(q.getId(), temp);
                    answers.addAll(temp);
                }
                Map<Long, Long> stats = new HashMap<>();
                for (Answer a : answers)
                    stats.put(a.getId(), 0L);

                List<QuestionAnswer> questionAnswers = Vars.questionAnswerRepository.readEntities(quan -> stats.containsKey(quan.getIdAnswer()));
                for (QuestionAnswer quan : questionAnswers) {
                    stats.put(quan.getIdAnswer(), stats.get(quan.getIdAnswer()) + 1);
                }

                for (Question q : questions) {
                    System.out.println(q.getText());

                    List<Answer> temp = answersPerQuestion.get(q.getId());
                    temp.sort(Comparator.comparingLong(DBEntity::getId));

                    for (int i = 0; i < temp.size(); i++) {
                        System.out.println((i + 1) + ". " + temp.get(i).getText() + ": " + stats.get(temp.get(i).getId()));
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
                index = Integer.parseInt(scanner.nextLine());
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
