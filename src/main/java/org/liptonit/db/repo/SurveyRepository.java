package org.liptonit.db.repo;

import org.liptonit.entity.Survey;
import org.liptonit.util.SearchCondition;

public class SurveyRepository{
    private Database db;
    private static SurveyRepository instance;

    public SurveyRepository(Database db) {this.db = db;}

    public static SurveyRepository getInstance(Database db) {
        if (instance == null)
            instance = new SurveyRepository(db);

        return instance;
    }

//    @Override
    public boolean createEntity(Survey entity) {
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
    public boolean updateEntityById(long id, Survey entity) {
        return db.updateEntityById(Survey.class, id, entity);
    }

//    @Override
    public boolean deleteEntityById(long id) {
        return db.deleteEntityById(Survey.class, id);
    }

//    @Override
    public boolean deleteEntities(SearchCondition<Survey> condition) {
        return db.deleteEntities(Survey.class, condition);
    }
}
