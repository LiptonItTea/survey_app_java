package org.liptonit.db.repo;

import org.liptonit.entity.Question;
import org.liptonit.util.SearchCondition;

public class QuestionRepository{
    private Database db;

    public QuestionRepository(Database db) {this.db = db;}

//    @Override
    public Question createEntity(Question entity) {
        return db.createEntity(Question.class, entity);
    }

//    @Override
    public Question readEntityById(long id) {
        return (Question) db.readEntityById(Question.class, id);
    }

//    @Override
    public Iterable<Question> readEntities(SearchCondition<Question> condition) {
        return db.readEntities(Question.class, condition);
    }

//    @Override
    public Question updateEntityById(long id, Question entity) {
        return db.updateEntityById(Question.class, id, entity);
    }

//    @Override
    public long deleteEntityById(long id) {
        return db.deleteEntityById(Question.class, id);
    }

//    @Override
    public Iterable<Long> deleteEntities(SearchCondition<Question> condition) {
        return db.deleteEntities(Question.class, condition);
    }
}
