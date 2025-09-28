package org.liptonit.db.repo;

import org.liptonit.entity.Question;
import org.liptonit.util.SearchCondition;

public class QuestionRepository implements Repository<Question>{
    private Database db;
    private static QuestionRepository instance;

    public QuestionRepository(Database db) {this.db = db;}

    public static QuestionRepository getInstance(Database db) {
        if (instance == null)
            instance = new QuestionRepository(db);

        return instance;
    }

    @Override
    public boolean createEntity(Question entity) {
        return db.createEntity(Question.class, entity);
    }

    @Override
    public Question readEntityById(long id) {
        return (Question) db.readEntityById(Question.class, id);
    }

    @Override
    public Iterable<Question> readEntities(SearchCondition<Question> condition) {
        return db.readEntities(Question.class, condition);
    }

    @Override
    public boolean updateEntityById(long id, Question entity) {
        return db.updateEntityById(Question.class, id, entity);
    }

    @Override
    public boolean deleteEntityById(long id) {
        return db.deleteEntityById(Question.class, id);
    }

    @Override
    public boolean deleteEntities(SearchCondition<Question> condition) {
        return db.deleteEntities(Question.class, condition);
    }
}
