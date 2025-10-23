package org.liptonit;

import org.liptonit.entity.*;

import java.time.LocalDate;
import java.util.*;

public class SurveyAppService {
    public static User signIn(String nickname, String password) {
        List<User> users = Vars.userRepository.readEntities(u -> u.getNickname().equals(nickname) &&
                u.getHashedPassword().equals(password));

        if (users.isEmpty())
            return null;

        return users.getFirst();
    }

    public static User signUp(String nickname, String email, String password) {
        List<User> users = Vars.userRepository.readEntities(u -> u.getNickname().equals(nickname));

        if (!users.isEmpty())
            return null;

        long id = Vars.userRepository.createEntity(new User(0, nickname, email, LocalDate.now(), password)).getId();
        return Vars.userRepository.readEntityById(id);
    }

    public static User editUser(String nickname, String password, String newNickname, String newEmail, String newPassword) {
        List<User> all = Vars.userRepository.readEntities(
                u -> u.getNickname().equals(nickname) && u.getHashedPassword().equals(password));

        if (all.isEmpty())
            return null;

        User u = all.getFirst();

        return Vars.userRepository.updateEntityById(u.getId(), patched -> {
            patched.setNickname(newNickname.equals("-") ? u.getNickname() : newNickname);
            patched.setEmail(newEmail.equals("-") ? u.getEmail() : newEmail);
            patched.setHashedPassword(newPassword.equals("-") ? u.getHashedPassword() : newPassword);
        });
    }

    public static User deleteUser(String nickname, String password) {
        List<User> all = Vars.userRepository.readEntities(
                u -> u.getNickname().equals(nickname) && u.getHashedPassword().equals(password));

        if (all.isEmpty())
            return null;

        return Vars.userRepository.deleteEntityById(all.getFirst().getId());
    }

    public static Survey createSurvey(String nickname, String password,
                                      String name, String description,
                                      String[] questionTexts, Boolean[] questionMultipleAnswers,
                                      String[][] answerTexts) {
        List<User> all = Vars.userRepository.readEntities(
                u -> u.getNickname().equals(nickname) && u.getHashedPassword().equals(password));

        if (all.isEmpty())
            return null;

        User currentUser = all.getFirst();

        Survey survey = Vars.surveyRepository.createEntity(new Survey(
                0, name, description, currentUser.getId()));
        long surveyId = survey.getId();

        for (int i = 0; i < questionTexts.length; i++){
            String qText = questionTexts[i];
            boolean multipleAnswers = questionMultipleAnswers[i];

            long questionId = Vars.questionRepository.createEntity(new Question(
                    0, qText, multipleAnswers, surveyId
            )).getId();

            for (int j = 0; j < answerTexts[i].length; j++) {
                String aText = answerTexts[i][j];

                Vars.answerRepository.createEntity(new Answer(
                        0, aText, questionId
                ));
            }
        }

        return survey;
    }

    public static CompletedSurvey conductSurvey(String nickname, String password,
                                                long surveyId, Long[] questionIds, Long[][] answerIds) {
        List<User> all = Vars.userRepository.readEntities(
                u -> u.getNickname().equals(nickname) && u.getHashedPassword().equals(password));

        if (all.isEmpty())
            return null;

        User currentUser = all.getFirst();

        Survey survey = Vars.surveyRepository.readEntityById(surveyId);
        if (survey == null)
            return null; //todo what if something other is wrong?

        CompletedSurvey completedSurvey = Vars.completedSurveyRepository.createEntity(new CompletedSurvey(
                0, currentUser.getId()));
        long completedSurveyId = completedSurvey.getId();

        for (int i = 0; i < questionIds.length; i++) {
            for (Long answerId : answerIds[i]) {
                if (Vars.answerRepository.readEntityById(answerId).getIdQuestion() != questionIds[i]) {
                    //rollback, wtf

                    Vars.completedSurveyRepository.deleteEntityById(completedSurveyId);
                    return null;
                }

                Vars.questionAnswerRepository.createEntity(new QuestionAnswer(
                        0, completedSurveyId, answerId
                ));
            }
        }

        return completedSurvey;
    }

    public static Map<Question, Map<Answer, Long>> getSurveyStatistics(String nickname, String password, long surveyId) {
        List<User> all = Vars.userRepository.readEntities(
                u -> u.getNickname().equals(nickname) && u.getHashedPassword().equals(password));

        if (all.isEmpty())
            return null;

        User currentUser = all.getFirst();

        if (Vars.surveyRepository.readEntityById(surveyId) == null)
            return null;

        if (Vars.surveyRepository.readEntityById(surveyId).getIdUserCreator() != currentUser.getId())
            return null;

        List<Question> questions = Vars.questionRepository.readEntities(q -> q.getIdSurvey() == surveyId);
        Map<Long, Long> answerToQuestions = new HashMap<>();
        Map<Question, Map<Answer, Long>> stats = new HashMap<>();

        for (Question q : questions) {
            List<Answer> temp = Vars.answerRepository.readEntities(a -> a.getIdQuestion() == q.getId());

            stats.put(q, new HashMap<>());
            for (Answer answer : temp) {
                stats.get(q).put(answer, 0L);
                answerToQuestions.put(answer.getId(), q.getId());
            }
        }

        List<QuestionAnswer> questionAnswers = Vars.questionAnswerRepository.readEntities(quan -> answerToQuestions.containsKey(quan.getIdAnswer()));
        for (QuestionAnswer quan : questionAnswers) {
            Map<Answer, Long> statPerAnswer = stats.get(
                Vars.questionRepository.readEntityById(
                        answerToQuestions.get(quan.getIdAnswer())
                )
            );

            statPerAnswer.put(
                    Vars.answerRepository.readEntityById(quan.getIdAnswer()),
                    statPerAnswer.get(Vars.answerRepository.readEntityById(quan.getIdAnswer())) + 1
            );
        }

        return stats;
    }
}
