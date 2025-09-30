package org.liptonit.db.repo;

import org.liptonit.entity.Answer;
import org.liptonit.util.SearchCondition;

import java.util.List;

public class AnswerRepository{
    private Database db;

    public AnswerRepository(Database db) {this.db = db;}

//    @Override
    public Answer createEntity(Answer entity) {
        return db.createEntity(Answer.class, entity);
    }

//    @Override
    public Answer readEntityById(long id) {
        return (Answer) db.readEntityById(Answer.class, id);
    }

//    @Override
    public List<Answer> readEntities(SearchCondition<Answer> condition) {
        return db.readEntities(Answer.class, condition);
    }

//    @Override
    public Answer updateEntityById(long id, Answer entity) {
        return db.updateEntityById(Answer.class, id, entity);
    }

//    @Override
    public long deleteEntityById(long id) {
        return db.deleteEntityById(Answer.class, id);
    }

//    @Override
    public List<Long> deleteEntities(SearchCondition<Answer> condition) {
        return db.deleteEntities(Answer.class, condition);
    }
}
