package org.liptonit.db.repo;

import org.liptonit.entity.User;
import org.liptonit.util.SearchCondition;

import java.util.List;

public class UserRepository{
    private Database db;

    public UserRepository(Database db) {this.db = db;}

//    @Override
    public User createEntity(User entity) {
        return db.createEntity(User.class, entity);
    }

//    @Override
    public User readEntityById(long id) {
        return (User) db.readEntityById(User.class, id);
    }

//    @Override
    public List<User> readEntities(SearchCondition<User> condition) {
        return db.readEntities(User.class, condition);
    }

//    @Override
    public User updateEntityById(long id, User entity) {
        return db.updateEntityById(User.class, id, entity);
    }

//    @Override
    public long deleteEntityById(long id) {
        return db.deleteEntityById(User.class, id);
    }

//    @Override
    public List<Long> deleteEntities(SearchCondition<User> condition) {
        return db.deleteEntities(User.class, condition);
    }
}
