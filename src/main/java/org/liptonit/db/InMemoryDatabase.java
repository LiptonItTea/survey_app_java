package org.liptonit.db;

import org.liptonit.entity.DBEntity;
import org.liptonit.util.SearchCondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase implements Database{
    private Map<Class<? extends DBEntity>, Map<Long, DBEntity>> repo;
    private static InMemoryDatabase instance;

    private InMemoryDatabase() {
        repo = new HashMap<>();
    }

    public static InMemoryDatabase getInstance() {
        if (instance == null)
            instance = new InMemoryDatabase();

        return instance;
    }

    private <T extends DBEntity> Map<Long, DBEntity> getEntityMap(Class<T> entityClass) {
        return repo.computeIfAbsent(entityClass, clz -> new HashMap<>());
    }

    @Override
    public <T extends DBEntity> boolean createEntity(Class<T> entityClass, T entity) {
        Map<Long, DBEntity> entityRepo = getEntityMap(entityClass);

        if (entityRepo.containsKey(entity.getId()))
            return false;

        entityRepo.put(entity.getId(), entity);
        return true;
    }

    @Override
    public <T extends DBEntity> DBEntity readEntityById(Class<T> entityClass, long id) {
        return getEntityMap(entityClass).getOrDefault(id, null);
    }

    @Override
    public <T extends DBEntity> Iterable<T> readEntities(Class<T> entityClass, SearchCondition<T> condition) {
        ArrayList<T> list = new ArrayList<>();

        for (Map.Entry<Long, DBEntity> e : getEntityMap(entityClass).entrySet()) {
            T t = (T) e.getValue(); // this is fine

            if (condition.select(t))
                list.add(t);
        }

        return list;
    }

    @Override
    public <T extends DBEntity> boolean updateEntityById(Class<T> entityClass, long id, T entity) throws IllegalArgumentException{
        if (!getEntityMap(entityClass).containsKey(id))
            return false;

        getEntityMap(entityClass).remove(id);
        getEntityMap(entityClass).put(id, entity);

        return true;
    }

    @Override
    public <T extends DBEntity> boolean deleteEntityById(Class<T> entityClass, long id) {
        if (!getEntityMap(entityClass).containsKey(id))
            return false;

        getEntityMap(entityClass).remove(id);
        return true;
    }

    @Override
    public <T extends DBEntity> boolean deleteEntities(Class<T> entityClass, SearchCondition<T> condition) {
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

        return result;
    }
}
