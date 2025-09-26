package org.liptonit.db;

import org.liptonit.entity.DBEntity;
import org.liptonit.util.SearchCondition;

import java.util.ArrayList;
import java.util.Map;

public interface Database {
    public <T extends DBEntity> boolean createEntity(Class<T> entityClass, T entity);

    public <T extends DBEntity> DBEntity readEntityById(Class<T> entityClass, long id);

    public <T extends DBEntity> Iterable<T> readEntities(Class<T> entityClass, SearchCondition<T> condition);

    public <T extends DBEntity> boolean updateEntityById(Class<T> entityClass, long id, T entity) throws IllegalArgumentException;

    public <T extends DBEntity> boolean deleteEntityById(Class<T> entityClass, long id);

    public <T extends DBEntity> boolean deleteEntities(Class<T> entityClass, SearchCondition<T> condition);
}
