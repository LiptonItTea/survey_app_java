package org.liptonit;

import org.liptonit.db.Database;
import org.liptonit.db.InMemoryDatabase;
import org.liptonit.db.Repository;
import org.liptonit.entity.Survey;
import org.liptonit.entity.User;

public class Vars {
    private static final Database db = new InMemoryDatabase();

//    public static AnswerRepository answerRepository = new AnswerRepository(db);
//    public static CompletedSurveyRepository completedSurveyRepository = new CompletedSurveyRepository(db);
//    public static QuestionAnswerRepository questionAnswerRepository = new QuestionAnswerRepository(db);
//    public static QuestionRepository questionRepository = new QuestionRepository(db);
//    public static SurveyRepository surveyRepository = new SurveyRepository(db, Survey.class);
    public static Repository<Survey> surveyRepository = new Repository<>(db, Survey.class);
    public static Repository<User> userRepository = new Repository<>(db, User.class);
}
