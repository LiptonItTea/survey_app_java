package org.liptonit.db.repo;

import org.liptonit.util.SearchCondition;

public interface Repository<T> {
    public T createEntity(T entity);

    public T readEntityById(long id);
    public Iterable<T> readEntities(SearchCondition<T> condition);

    public T updateEntityById(long id, T entity);

    public T deleteEntityById(long id2);
    public T deleteEntities(SearchCondition<T> condition);
}
