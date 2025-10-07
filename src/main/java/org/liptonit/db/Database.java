package org.liptonit.db;

import org.liptonit.entity.DBEntity;
import org.liptonit.util.Patcher;
import org.liptonit.util.SearchCondition;

import java.util.List;

public abstract class Database {
    protected abstract  <T extends DBEntity> T createEntity(Class<T> entityClass, T entity);

    protected abstract <T extends DBEntity> T readEntityById(Class<T> entityClass, long id);

    protected abstract <T extends DBEntity> List<T> readEntities(Class<T> entityClass, SearchCondition<T> condition);

    protected abstract <T extends DBEntity> T updateEntityById(Class<T> entityClass, long id, Patcher<T> patcher) throws IllegalArgumentException;

    protected abstract <T extends DBEntity> List<T> updateEntities(Class<T> entityClass, SearchCondition<T> condition, Patcher<T> patcher);

    protected abstract <T extends DBEntity> T deleteEntityById(Class<T> entityClass, long id);

    protected abstract <T extends DBEntity> List<Long> deleteEntities(Class<T> entityClass, SearchCondition<T> condition);
}
