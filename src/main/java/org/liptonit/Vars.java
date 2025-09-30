package org.liptonit;

import org.liptonit.db.InMemoryDatabase;
import org.liptonit.db.repo.*;

public class Vars {
    private static final Database db = new InMemoryDatabase();

    public static AnswerRepository answerRepository = new AnswerRepository(db);
    public static CompletedSurveyRepository completedSurveyRepository = new CompletedSurveyRepository(db);
    public static QuestionAnswerRepository questionAnswerRepository = new QuestionAnswerRepository(db);
    public static QuestionRepository questionRepository = new QuestionRepository(db);
    public static SurveyRepository surveyRepository = new SurveyRepository(db);
    public static UserRepository userRepository = new UserRepository(db);
}
