package org.liptonit;

import org.liptonit.db.Database;
import org.liptonit.db.InMemoryDatabase;
import org.liptonit.db.Repository;
import org.liptonit.entity.*;

public class Vars {
    private static final Database db = new InMemoryDatabase();

    public static final Repository<Answer> answerRepository = new Repository<>(db, Answer.class);
    public static final Repository<CompletedSurvey> completedSurveyRepository = new Repository<>(db, CompletedSurvey.class);
    public static final Repository<QuestionAnswer> questionAnswerRepository = new Repository<>(db, QuestionAnswer.class);
    public static final Repository<Question> questionRepository = new Repository<>(db, Question.class);
    public static final Repository<Survey> surveyRepository = new Repository<>(db, Survey.class);
    public static final Repository<User> userRepository = new Repository<>(db, User.class);
}
