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

    private static User currentUser = null;
    private static Entry[] start = null;
    private static Entry[] interact = null;

    static {
        start = new Entry[]{
                new Entry("Sign in", scanner -> {
                    System.out.print("Nickname:\n> ");
                    String nickname = scanner.nextLine();

                    System.out.print("Password:\n> ");
                    String password = scanner.nextLine();

                    List<User> users = Vars.userRepository.readEntities(u -> u.getNickname().equals(nickname) &&
                            u.getHashedPassword().equals(password));

                    if (users.isEmpty()) {
                        System.out.print("O holera, no such user found!");
                        return false;
                    }

                    currentUser = users.getFirst();
                    swap(interact);
                    return true;
                }),
                new Entry("Sign up", scanner -> {
                    System.out.print("Nickname:\n> ");
                    String nickname = scanner.nextLine();

                    System.out.print("email:\n> ");
                    String email = scanner.nextLine();

                    System.out.print("Password:\n> ");
                    String password = scanner.nextLine();


                    List<User> users = Vars.userRepository.readEntities(u -> u.getNickname().equals(nickname));

                    if (!users.isEmpty()) {
                        System.out.print("O holera, user with the same nickname has been found!");
                        return false;
                    }

                    long id = Vars.userRepository.createEntity(new User(0, nickname, email, LocalDate.now(), password)).getId();
                    currentUser = Vars.userRepository.readEntityById(id);
                    swap(interact);
                    return true;
                })
        };

        interact = new Entry[]{
                new Entry("Edit user", scanner -> {
                    List<User> all = Vars.userRepository.readEntities(
                            u -> u.getNickname().equals(currentUser.getNickname()));

                    if (all.isEmpty()) {
                        System.out.println("No such user found.");
                        return false;
                    }

                    User u = all.getFirst();

                    System.out.print("New nickname, - if same\n> ");
                    String newNickname = scanner.nextLine();

                    System.out.print("New email, - if same\n> ");
                    String email = scanner.nextLine();

                    System.out.print("New password, - if same\n> ");
                    String newPassword = scanner.nextLine();

                    Vars.userRepository.updateEntityById(u.getId(), patched -> {
                        patched.setNickname(newNickname.equals("-") ? u.getNickname() : newNickname);
                        patched.setEmail(email.equals("-") ? u.getEmail() : email);
                        patched.setHashedPassword(newPassword.equals("-") ? u.getHashedPassword() : newPassword);
                    });
                    return true;
                }),

                new Entry("Delete user", scanner -> {
                    List<User> all = Vars.userRepository.readEntities(
                            u -> u.getNickname().equals(currentUser.getNickname()));

                    if (all.isEmpty()) {
                        System.out.print("No such user found.");
                        return false;
                    }

                    Vars.userRepository.deleteEntityById(currentUser.getId());
                    swap(start);
                    return true;
                }),

                new Entry("Create survey", scanner -> {
                    List<User> all = Vars.userRepository.readEntities(
                            u -> u.getNickname().equals(currentUser.getNickname()));

                    if (all.isEmpty()) {
                        System.out.print("No such user found.");
                        return false;
                    }

                    System.out.print("Survey name\n> ");
                    String name = scanner.nextLine();

                    System.out.print("Survey description\n> ");
                    String descr = scanner.nextLine();

                    long surveyId = Vars.surveyRepository.createEntity(new Survey(
                            0, name, descr, currentUser.getId()
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

                new Entry("Conduct survey", scanner -> {
                    List<User> all = Vars.userRepository.readEntities(
                            u -> u.getNickname().equals(currentUser.getNickname()));

                    if (all.isEmpty()) {
                        System.out.print("No such user found.");
                    }

                    System.out.print("Survey ID\n> ");
                    long surveyId = Long.parseLong(scanner.nextLine());

                    Survey survey = Vars.surveyRepository.readEntityById(surveyId);
                    if (survey == null) {
                        System.out.println("Can't find any survey with given ID.");
                        return false;
                    }

                    long completedSurveyId = Vars.completedSurveyRepository.createEntity(new CompletedSurvey(
                            0, currentUser.getId()
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
                    List<User> all = Vars.userRepository.readEntities(
                            u -> u.getNickname().equals(currentUser.getNickname()));

                    if (all.isEmpty()) {
                        System.out.print("No such user found.");
                    }

                    System.out.print("Survey ID\n> ");
                    long surveyId = Long.parseLong(scanner.nextLine());

                    if (Vars.surveyRepository.readEntityById(surveyId) == null) {
                        System.out.print("No such survey found.");
                        return false;
                    }

                    if (Vars.surveyRepository.readEntityById(surveyId).getIdUserCreator() != currentUser.getId()) {
                        System.out.println("This survey doesn't belong to you,");
                        return false;
                    }

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
                }),

                new Entry("ADMIN List all users", scanner -> {
                    for (User u : Vars.userRepository.readEntities(u -> true))
                        System.out.println(u);
                    return true;
                }),

                new Entry("ADMIN List all surveys", scanner -> {
                    for (Survey s : Vars.surveyRepository.readEntities(s -> true))
                        System.out.println(s);

                    return true;
                }),
        };

    }

    private static Entry[] currentEntries = null;

    private static void swap(Entry[] entries) {
        System.out.print("\n");
        currentEntries = entries;
        for (int i = 0; i < currentEntries.length; i++) {
            System.out.print((i + 1) + " " + currentEntries[i].description + "\n");
        }
        System.out.print("-1 Exit\n");
    }

    private static void poll(Scanner scanner) {
        while (true) {
            System.out.print("> ");

            int index;
            try {
                index = Integer.parseInt(scanner.nextLine());
            }
            catch (NumberFormatException e) {
                System.err.print("Oops, wrong command!\n");
                continue;
            }

            if (index == -1) {
                System.out.print("Gracefully exiting...\n");
                return;
            }
            if (index > currentEntries.length) {
                System.out.print("Index must be between 1 and " + currentEntries.length + "\n");
                continue;
            }
            System.out.print("\n");
            boolean result = currentEntries[index - 1].command.execute(scanner);
            System.out.print("\n");
        }
    }

    public static void run() {
        Scanner scanner = new Scanner(System.in);

        // todo how2test?

        swap(start);
        poll(scanner);
    }
}
