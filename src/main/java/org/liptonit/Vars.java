package org.liptonit;

import org.liptonit.db.Database;
import org.liptonit.db.InMemoryDatabase;
import org.liptonit.db.Repository;
import org.liptonit.entity.*;

public class Vars {
    private static final Database db = new InMemoryDatabase();

    public static Repository<Answer> answerRepository = new Repository<>(db, Answer.class);
    public static Repository<CompletedSurvey> completedSurveyRepository = new Repository<>(db, CompletedSurvey.class);
    public static Repository<QuestionAnswer> questionAnswerRepository = new Repository<>(db, QuestionAnswer.class);
    public static Repository<Question> questionRepository = new Repository<>(db, Question.class);
    public static Repository<Survey> surveyRepository = new Repository<>(db, Survey.class);
    public static Repository<User> userRepository = new Repository<>(db, User.class);
}
