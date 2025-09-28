package org.liptonit.db.repo;

import org.liptonit.entity.DBEntity;
import org.liptonit.util.SearchCondition;

public abstract class Database {
    protected abstract  <T extends DBEntity> boolean createEntity(Class<T> entityClass, T entity);

    protected abstract <T extends DBEntity> DBEntity readEntityById(Class<T> entityClass, long id);

    protected abstract <T extends DBEntity> Iterable<T> readEntities(Class<T> entityClass, SearchCondition<T> condition);

    protected abstract <T extends DBEntity> boolean updateEntityById(Class<T> entityClass, long id, T entity) throws IllegalArgumentException;

    protected abstract <T extends DBEntity> boolean deleteEntityById(Class<T> entityClass, long id);

    protected abstract <T extends DBEntity> boolean deleteEntities(Class<T> entityClass, SearchCondition<T> condition);
}
