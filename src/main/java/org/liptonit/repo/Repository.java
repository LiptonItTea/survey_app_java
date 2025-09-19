package org.liptonit.repo;

import org.liptonit.util.SearchCondition;

import java.util.function.Consumer;

public interface Repository<T> {
    public void createEntity(T entity);
    public T readEntity(SearchCondition<T> condition);
}
