package org.liptonit.db;

import org.liptonit.db.repo.Database;
import org.liptonit.entity.DBEntity;
import org.liptonit.util.SearchCondition;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase extends Database {
    private Map<Class<? extends DBEntity>, Map<Long, DBEntity>> repo;
    private Map<Class<? extends DBEntity>, Long> sequences;

    public InMemoryDatabase() {
        repo = new HashMap<>();
        sequences = new HashMap<>();
    }

    private <T extends DBEntity> Map<Long, DBEntity> getEntityMap(Class<T> entityClass) {
        return repo.computeIfAbsent(entityClass, clz -> new HashMap<>());
    }

    @Override
    protected <T extends DBEntity> T createEntity(Class<T> entityClass, T entity) {
        Map<Long, DBEntity> entityRepo = getEntityMap(entityClass);
        Long id = sequences.computeIfAbsent(entityClass, clz -> 0L);

        try {
            T e = entityClass.getConstructor(Long.class, entityClass).newInstance(id, entity);
            entityRepo.put(id, e);

            sequences.put(entityClass, sequences.get(entityClass) + 1);
            return e;
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("Looks like your entity class is missing constructor, or your constructor is not public, or your class doesn't have constructor(Long, DBEntity). You dumbass.");
        }
    }

    @Override
    protected  <T extends DBEntity> T readEntityById(Class<T> entityClass, long id) {
        return (T) getEntityMap(entityClass).get(id);
    }

    @Override
    protected <T extends DBEntity> Iterable<T> readEntities(Class<T> entityClass, SearchCondition<T> condition) {
        ArrayList<T> list = new ArrayList<>();

        for (Map.Entry<Long, DBEntity> e : getEntityMap(entityClass).entrySet()) {
            T t = (T) e.getValue(); // this is fine

            if (condition.select(t))
                list.add(t);
        }

        return list;
    }

    @Override
    protected <T extends DBEntity> T updateEntityById(Class<T> entityClass, long id, T entity) throws IllegalArgumentException{
        if (!getEntityMap(entityClass).containsKey(id))
            return null;

        getEntityMap(entityClass).remove(id);
        getEntityMap(entityClass).put(id, entity);

        return entity;
    }

    @Override
    protected <T extends DBEntity> long deleteEntityById(Class<T> entityClass, long id) {
        if (!getEntityMap(entityClass).containsKey(id))
            return -1;

        getEntityMap(entityClass).remove(id);
        return id;
    }

    @Override
    protected <T extends DBEntity> Iterable<Long> deleteEntities(Class<T> entityClass, SearchCondition<T> condition) {
        boolean result = false;
        ArrayList<Long> ids = new ArrayList<>();

        Map<Long, DBEntity> map = getEntityMap(entityClass);

        for (Map.Entry<Long, DBEntity> e : map.entrySet()) {
            T t = (T) e.getValue(); // this is fine

            if (condition.select(t)) {
                result = true;

                ids.add(e.getKey());
            }
        }

        for (Long id : ids)
            map.remove(id);

        return ids;
    }
}
