package org.liptonit.db.repo;

import org.liptonit.util.SearchCondition;

public interface Repository<T> {
    public boolean createEntity(T entity);

    public T readEntityById(long id);
    public Iterable<T> readEntities(SearchCondition<T> condition);

    public boolean updateEntityById(long id, T entity);

    public boolean deleteEntityById(long id2);
    public boolean deleteEntities(SearchCondition<T> condition);
}
