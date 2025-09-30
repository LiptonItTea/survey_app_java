package org.liptonit.db.repo;

import org.liptonit.entity.Survey;
import org.liptonit.util.SearchCondition;

import java.util.List;

public class SurveyRepository{
    private Database db;

    public SurveyRepository(Database db) {this.db = db;}

//    @Override
    public Survey createEntity(Survey entity) {
        return db.createEntity(Survey.class, entity);
    }

//    @Override
    public Survey readEntityById(long id) {
        return db.readEntityById(Survey.class, id);
    }

//    @Override
    public List<Survey> readEntities(SearchCondition<Survey> condition) {
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
    public List<Long> deleteEntities(SearchCondition<Survey> condition) {
        return db.deleteEntities(Survey.class, condition);
    }
}
