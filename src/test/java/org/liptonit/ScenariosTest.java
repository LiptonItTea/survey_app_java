package org.liptonit;

import org.junit.jupiter.api.Test;
import org.liptonit.entity.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ScenariosTest {

    @Test
    void testSignInSuccessful() {
        // Arrange
        String nickname = "testuser_success";
        String password = "password";
        User signedUpUser = Scenarios.signUp(nickname, "test_success@example.com", password);

        // Act
        User user = Scenarios.signIn(nickname, password);

        // Assert
        assertNotNull(user);
        assertEquals(nickname, user.getNickname());
        assertEquals(1, signedUpUser.getId());
    }

    @Test
    void testSignInWrongPassword() {
        // Arrange
        String nickname = "testuser_wrongpass";
        String password = "password";
        Scenarios.signUp(nickname, "test_wrongpass@example.com", password);

        // Act
        User user = Scenarios.signIn(nickname, "wrongpassword");

        // Assert
        assertNull(user);
    }

    @Test
    void testSignInNonExistentUser() {
        // Act
        User user = Scenarios.signIn("nonexistentuser", "password");

        // Assert
        assertNull(user);
    }

    @Test
    void testCreateSurveySuccessful() {
        // Arrange
        User user = Scenarios.signUp("testuser_survey", "test_survey@example.com", "password");
        String surveyName = "Test Survey";
        String surveyDescription = "This is a test survey.";
        String[] questionTexts = {"Question 1", "Question 2"};
        Boolean[] questionMultipleAnswers = {false, true};
        String[][] answerTexts = {{"Answer 1.1", "Answer 1.2"}, {"Answer 2.1", "Answer 2.2"}};

        // Act
        Survey survey = Scenarios.createSurvey(user.getNickname(), "password", surveyName, surveyDescription, questionTexts, questionMultipleAnswers, answerTexts);

        // Assert
        assertNotNull(survey);
        assertEquals(surveyName, survey.getName());
        assertEquals(surveyDescription, survey.getDescription());
        assertEquals(user.getId(), survey.getIdUserCreator());

        List<Question> questions = Vars.questionRepository.readEntities(q -> q.getIdSurvey() == survey.getId());
        assertEquals(2, questions.size());

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            assertEquals(questionTexts[i], question.getText());
            assertEquals(questionMultipleAnswers[i], question.isMultipleAnswers());

            List<Answer> answers = Vars.answerRepository.readEntities(a -> a.getIdQuestion() == question.getId());
            assertEquals(2, answers.size());

            for (int j = 0; j < answers.size(); j++) {
                Answer answer = answers.get(j);
                assertEquals(answerTexts[i][j], answer.getText());
            }
        }
    }

    @Test
    void testConductSurveySuccessful() {
        // Arrange
        User user = Scenarios.signUp("testuser_conduct", "test_conduct@example.com", "password");
        Survey survey = Scenarios.createSurvey(user.getNickname(), "password", "Test Survey", "Description", new String[]{"Q1"}, new Boolean[]{false}, new String[][]{{"A1", "A2"}});
        List<Question> questions = Vars.questionRepository.readEntities(q -> q.getIdSurvey() == survey.getId());
        List<Answer> answers = Vars.answerRepository.readEntities(a -> a.getIdQuestion() == questions.getFirst().getId());

        Long[] questionIds = {questions.getFirst().getId()};
        Long[][] answerIds = {{answers.getFirst().getId()}};

        // Act
        CompletedSurvey completedSurvey = Scenarios.conductSurvey(user.getNickname(), "password", survey.getId(), questionIds, answerIds);

        // Assert
        assertNotNull(completedSurvey);
        assertEquals(user.getId(), completedSurvey.getIdUser());

        List<QuestionAnswer> questionAnswers = Vars.questionAnswerRepository.readEntities(qa -> qa.getIdCompletedSurvey() == completedSurvey.getId());
        assertEquals(1, questionAnswers.size());
        assertEquals(answers.getFirst().getId(), questionAnswers.getFirst().getIdAnswer());
    }

    @Test
    void testGetSurveyStatisticsSuccessful() {
        // Arrange
        User user = Scenarios.signUp("testuser_stats", "test_stats@example.com", "password");
        Survey survey = Scenarios.createSurvey(user.getNickname(), "password", "Test Survey", "Description", new String[]{"Q1"}, new Boolean[]{false}, new String[][]{{"A1", "A2"}});
        List<Question> questions = Vars.questionRepository.readEntities(q -> q.getIdSurvey() == survey.getId());
        List<Answer> answers = Vars.answerRepository.readEntities(a -> a.getIdQuestion() == questions.getFirst().getId());

        Long[] questionIds = {questions.getFirst().getId()};
        Long[][] answerIds = {{answers.getFirst().getId()}};

        Scenarios.conductSurvey(user.getNickname(), "password", survey.getId(), questionIds, answerIds);

        // Act
        Map<Question, Map<Answer, Long>> stats = Scenarios.getSurveyStatistics(user.getNickname(), "password", survey.getId());

        // Assert
        assertNotNull(stats);
        assertEquals(1, stats.size());

        Map<Answer, Long> answerStats = stats.get(questions.getFirst());
        assertEquals(2, answerStats.size());

        assertEquals(1L, (long) answerStats.get(answers.getFirst()));
        assertEquals(0L, (long) answerStats.get(answers.get(1)));
    }
}