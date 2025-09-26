package org.liptonit.db;

import org.liptonit.db.repo.SurveyRepository;
import org.liptonit.db.repo.UserRepository;
import org.liptonit.entity.DBEntity;
import org.liptonit.entity.Survey;
import org.liptonit.entity.User;
import org.liptonit.util.SearchCondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase{
    private Map<Class<? extends DBEntity>, Map<Long, DBEntity>> repo;

    public InMemoryDatabase() {
        repo = new HashMap<>();
    }

    public Map<Long, DBEntity> getEntityMap(Class<? extends DBEntity> entityClass) {
        return repo.computeIfAbsent(entityClass, clz -> new HashMap<>());
    }

    public <T extends DBEntity> boolean createEntity(Class<T> entityClass, T entity) {
        Map<Long, DBEntity> entityRepo = getEntityMap(entityClass);

        if (entityRepo.containsKey(entity.getId()))
            return false;

        entityRepo.put(entity.getId(), entity);
        return true;
    }

    public <T extends DBEntity> User readEntityById(Class<? extends DBEntity> entityClass, long id) {
        return getEntityMap(entityClass).getOrDefault(id, null);
    }

    public Iterable<User> readEntities(SearchCondition<User> condition) {
        ArrayList<User> list = new ArrayList<>();
        for (Map.Entry<Long, User> e : userRepo.entrySet())
            if (condition.select(e.getValue()))
                list.add(e.getValue());

        return list;
    }

    public User updateEntityById(long id, User entity) throws IllegalArgumentException{
        if (!userRepo.containsKey(id))
            return false;

        User u = userRepo.get(id);
        u.setNickname(entity.getEmail());
        u.setEmail(entity.getEmail());
        u.setHashedPassword(entity.getHashedPassword());
        u.setRegistrationDate(entity.getRegistrationDate());

        return true;
    }

    public User deleteEntityById(long id) {
        if (!userRepo.containsKey(id))
            return false;

        userRepo.remove(id);
        return true;
    }

    public User deleteEntities(SearchCondition<User> condition) {
        boolean result = false;
        ArrayList<Long> ids = new ArrayList<>();

        for (Map.Entry<Long, User> e : userRepo.entrySet()) {
            if (condition.select(e.getValue())) {
                result = true;

                ids.add(e.getKey());
            }
        }

        for (Long id : ids)
            userRepo.remove(id);

        return result;
    }

    public Survey deleteEntities(SearchCondition<Survey> condition) {
        boolean result = false;
        ArrayList<Long> ids = new ArrayList<>();

        for (Map.Entry<Long, User> e : userRepo.entrySet()) {
            if (condition.select(e.getValue())) {
                result = true;

                ids.add(e.getKey());
            }
        }

        for (Long id : ids)
            userRepo.remove(id);

        return result;
    }
}
