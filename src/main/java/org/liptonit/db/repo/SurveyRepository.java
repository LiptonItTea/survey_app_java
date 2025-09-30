package org.liptonit.db.repo;

import org.liptonit.entity.Survey;
import org.liptonit.util.SearchCondition;

public class SurveyRepository{
    private Database db;

    public SurveyRepository(Database db) {this.db = db;}

//    @Override
    public Survey createEntity(Survey entity) {
        return db.createEntity(Survey.class, entity);
    }

//    @Override
    public Survey readEntityById(long id) {
        return (Survey) db.readEntityById(Survey.class, id);
    }

//    @Override
    public Iterable<Survey> readEntities(SearchCondition<Survey> condition) {
        return db.readEntities(Survey.class, condition);
    }

//    @Override
    public Survey updateEntityById(long id, Survey entity) {
        return db.updateEntityById(Survey.class, id, entity);
    }

//    @Override
    public long deleteEntityById(long id) {
        return db.deleteEntityById(Survey.class, id);
    }

//    @Override
    public Iterable<Long> deleteEntities(SearchCondition<Survey> condition) {
        return db.deleteEntities(Survey.class, condition);
    }
}
