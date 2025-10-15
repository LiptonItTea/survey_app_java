package org.liptonit.cli;

import org.liptonit.SurveyAppService;
import org.liptonit.Vars;
import org.liptonit.entity.*;

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

                    User u = SurveyAppService.signIn(nickname, password);
                    if (u == null) {
                        System.out.print("No such user found\n");
                        return false;
                    }

                    currentUser = u;
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

                    User u = SurveyAppService.signUp(nickname, email, password);
                    if (u == null) {
                        System.out.print("User with such nickname has already been registered.");
                        return false;
                    }

                    currentUser = u;
                    swap(interact);
                    return true;
                })
        };

        interact = new Entry[]{
                new Entry("Edit user", scanner -> {
                    System.out.print("New nickname, - if same\n> ");
                    String newNickname = scanner.nextLine();

                    System.out.print("New email, - if same\n> ");
                    String email = scanner.nextLine();

                    System.out.print("New password, - if same\n> ");
                    String newPassword = scanner.nextLine();

                    User u = SurveyAppService.editUser(currentUser.getNickname(), currentUser.getHashedPassword(), newNickname, email, newPassword);
                    if (u == null) {
                        System.out.print("Something went wrong.\n");
                        return false;
                    }

                    currentUser = u;
                    return true;
                }),

                new Entry("Delete user", scanner -> {
                    User u = SurveyAppService.deleteUser(currentUser.getNickname(), currentUser.getHashedPassword());

                    if (u == null) {
                        System.out.print("Something went wrong.\n");
                        return false;
                    }

                    swap(start);
                    return true;
                }),

                new Entry("Create survey", scanner -> {
                    System.out.print("Survey name\n> ");
                    String name = scanner.nextLine();

                    System.out.print("Survey description\n> ");
                    String descr = scanner.nextLine();

                    ArrayList<String> questionTexts = new ArrayList<>();
                    ArrayList<Boolean> questionMultipleAnswers = new ArrayList<>();
                    ArrayList<String[]> answers = new ArrayList<>();
                    while (true) {
                        System.out.print("Question text, - if no question\n> ");
                        String qText = scanner.nextLine();
                        if (qText.equals("-"))
                            break;
                        questionTexts.add(qText);

                        System.out.print("Multiple answers\n> ");
                        boolean multipleAnswers = Boolean.parseBoolean(scanner.nextLine());
                        questionMultipleAnswers.add(multipleAnswers);

                        ArrayList<String> questionAnswers = new ArrayList<>();
                        while (true) {
                            System.out.print("Answer text, - if no answer\n> ");
                            String aText = scanner.nextLine();
                            if (aText.equals("-"))
                                break;
                            questionAnswers.add(aText);
                        }
                        answers.add(questionAnswers.toArray(new String[0]));
                    }

                    Survey survey = SurveyAppService.createSurvey(
                            currentUser.getNickname(), currentUser.getHashedPassword(),
                            name, descr,
                            questionTexts.toArray(new String[0]), questionMultipleAnswers.toArray(new Boolean[0]), answers.toArray(new String[0][0]));

                    if (survey == null) {
                        System.out.print("Something went wrong.\n");
                        return false;
                    }

                    return true;
                }),

                new Entry("Conduct survey", scanner -> {
                    System.out.print("Survey ID\n> ");
                    long surveyId = Long.parseLong(scanner.nextLine());

                    Survey survey = Vars.surveyRepository.readEntityById(surveyId);
                    if (survey == null) {
                        System.out.println("Can't  find any survey with given ID.");
                        return false;
                    }

                    List<Question> questions = Vars.questionRepository.readEntities(q -> q.getIdSurvey() == surveyId);
                    ArrayList<Long> questionIds = new ArrayList<>();
                    ArrayList<Long[]> answerIds = new ArrayList<>();

                    for (Question q : questions) {
                        questionIds.add(q.getId());

                        List<Answer> answers = Vars.answerRepository.readEntities(a -> a.getIdQuestion() == q.getId());

                        System.out.println(q.getText());
                        for (int i = 0; i < answers.size(); i++)
                            System.out.println((i + 1) + ". " + answers.get(i).getText());

                        ArrayList<Long> questionAnswers = new ArrayList<>();
                        while (true) {
                            System.out.print("> ");
                            String data = scanner.nextLine();

                            if (data.equals("-"))
                                break;

                            int variant = Integer.parseInt(data);
                            long answerId = answers.get(variant - 1).getId();
                            questionAnswers.add(answerId);

                            if (!q.isMultipleAnswers())
                                break;
                        }

                        answerIds.add(questionAnswers.toArray(new Long[0]));
                    }

                    CompletedSurvey completedSurvey = SurveyAppService.conductSurvey(
                            currentUser.getNickname(), currentUser.getHashedPassword(),
                            surveyId, questionIds.toArray(new Long[0]), answerIds.toArray(new Long[0][0])
                    );

                    if (completedSurvey == null) {
                        System.out.println("Something went wrong.");
                        return false;
                    }
                    return true;
                }),

                new Entry("Get survey statistics", scanner -> {
                    System.out.print("Survey ID\n> ");
                    long surveyId = Long.parseLong(scanner.nextLine());

                    Map<Question, Map<Answer, Long>> stats = SurveyAppService.getSurveyStatistics(
                            currentUser.getNickname(), currentUser.getHashedPassword(), surveyId
                    );

                    if (stats == null) {
                        System.out.println("Something went wrong.");
                        return false;
                    }

                    for (Map.Entry<Question, Map<Answer, Long>> e : stats.entrySet()) {
                        Question question = e.getKey();
                        System.out.println(question.getText());

                        int i = 0;
                        for (Map.Entry<Answer, Long> stat : e.getValue().entrySet()) {
                            System.out.println((i++ + 1) + ". " + stat.getKey().getText() + ": " + stat.getValue());
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
