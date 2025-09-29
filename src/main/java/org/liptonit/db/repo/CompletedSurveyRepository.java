package org.liptonit.db.repo;

import org.liptonit.entity.CompletedSurvey;
import org.liptonit.util.SearchCondition;

public class CompletedSurveyRepository{
    private Database db;
    private static CompletedSurveyRepository instance;

    public CompletedSurveyRepository(Database db) {this.db = db;}

    public static CompletedSurveyRepository getInstance(Database db) {
        if (instance == null)
            instance = new CompletedSurveyRepository(db);

        return instance;
    }

//    @Override
    public CompletedSurvey createEntity(CompletedSurvey entity) {
        return db.createEntity(CompletedSurvey.class, entity);
    }

//    @Override
    public CompletedSurvey readEntityById(long id) {
        return (CompletedSurvey) db.readEntityById(CompletedSurvey.class, id);
    }

//    @Override
    public Iterable<CompletedSurvey> readEntities(SearchCondition<CompletedSurvey> condition) {
        return db.readEntities(CompletedSurvey.class, condition);
    }

//    @Override
    public CompletedSurvey updateEntityById(long id, CompletedSurvey entity) {
        return db.updateEntityById(CompletedSurvey.class, id, entity);
    }

//    @Override
    public long deleteEntityById(long id) {
        return db.deleteEntityById(CompletedSurvey.class, id);
    }

//    @Override
    public Iterable<Long> deleteEntities(SearchCondition<CompletedSurvey> condition) {
        return db.deleteEntities(CompletedSurvey.class, condition);
    }
}
