package org.liptonit.db.repo;

import org.liptonit.entity.QuestionAnswer;
import org.liptonit.util.SearchCondition;

public class QuestionAnswerRepository{
    private Database db;
    private static QuestionAnswerRepository instance;

    public QuestionAnswerRepository(Database db) {this.db = db;}

    public static QuestionAnswerRepository getInstance(Database db) {
        if (instance == null)
            instance = new QuestionAnswerRepository(db);

        return instance;
    }

//    @Override
    public QuestionAnswer createEntity(QuestionAnswer entity) {
        return db.createEntity(QuestionAnswer.class, entity);
    }

//    @Override
    public QuestionAnswer readEntityById(long id) {
        return (QuestionAnswer) db.readEntityById(QuestionAnswer.class, id);
    }

//    @Override
    public Iterable<QuestionAnswer> readEntities(SearchCondition<QuestionAnswer> condition) {
        return db.readEntities(QuestionAnswer.class, condition);
    }

//    @Override
    public QuestionAnswer updateEntityById(long id, QuestionAnswer entity) {
        return db.updateEntityById(QuestionAnswer.class, id, entity);
    }

//    @Override
    public long deleteEntityById(long id) {
        return db.deleteEntityById(QuestionAnswer.class, id);
    }

//    @Override
    public Iterable<Long> deleteEntities(SearchCondition<QuestionAnswer> condition) {
        return db.deleteEntities(QuestionAnswer.class, condition);
    }
}
