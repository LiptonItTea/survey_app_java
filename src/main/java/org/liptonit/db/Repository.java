package org.liptonit.db;

import org.liptonit.entity.DBEntity;
import org.liptonit.entity.Survey;
import org.liptonit.util.Patcher;
import org.liptonit.util.SearchCondition;

import java.util.List;

public class Repository<T extends DBEntity> {
    private Database db;
    private Class<T> entityClass;

    public Repository(Database db, Class<T> entityClass) {
        this.db = db;
        this.entityClass = entityClass;
    }

    public T createEntity(T entity) {
        return db.createEntity(entityClass, entity);
    }

    public T readEntityById(long id) {
        return db.readEntityById(entityClass, id);
    }

    public List<T> readEntities(SearchCondition<T> condition) {
        return db.readEntities(entityClass, condition);
    }

    public T updateEntityById(long id, Patcher<T> patcher) {
        return db.updateEntityById(entityClass, id, patcher);
    }

    public List<T> updateEntities(SearchCondition<T> condition, Patcher<T> patcher) {
        return db.updateEntities(entityClass, condition, patcher);
    }

    public T deleteEntityById(long id) {
        return db.deleteEntityById(entityClass, id);
    }

    public List<T> deleteEntities(SearchCondition<T> condition) {
        return db.deleteEntities(entityClass, condition);
    }
}
