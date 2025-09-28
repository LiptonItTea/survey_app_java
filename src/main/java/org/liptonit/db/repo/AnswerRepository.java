package org.liptonit.db.repo;

import org.liptonit.entity.Answer;
import org.liptonit.util.SearchCondition;

public class AnswerRepository{
    private Database db;
    private static AnswerRepository instance;

    public AnswerRepository(Database db) {this.db = db;}

    public static AnswerRepository getInstance(Database db) {
        if (instance == null)
            instance = new AnswerRepository(db);

        return instance;
    }

//    @Override
    public boolean createEntity(Answer entity) {
        return db.createEntity(Answer.class, entity);
    }

//    @Override
    public Answer readEntityById(long id) {
        return (Answer) db.readEntityById(Answer.class, id);
    }

//    @Override
    public Iterable<Answer> readEntities(SearchCondition<Answer> condition) {
        return db.readEntities(Answer.class, condition);
    }

//    @Override
    public boolean updateEntityById(long id, Answer entity) {
        return db.updateEntityById(Answer.class, id, entity);
    }

//    @Override
    public boolean deleteEntityById(long id) {
        return db.deleteEntityById(Answer.class, id);
    }

//    @Override
    public boolean deleteEntities(SearchCondition<Answer> condition) {
        return db.deleteEntities(Answer.class, condition);
    }
}
