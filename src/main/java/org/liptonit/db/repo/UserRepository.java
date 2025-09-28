package org.liptonit.db.repo;

import org.liptonit.entity.User;
import org.liptonit.util.SearchCondition;

public class UserRepository{
    private Database db;
    private static UserRepository instance;

    public UserRepository(Database db) {this.db = db;}

    public static UserRepository getInstance(Database db) {
        if (instance == null)
            instance = new UserRepository(db);

        return instance;
    }

//    @Override
    public boolean createEntity(User entity) {
        return db.createEntity(User.class, entity);
    }

//    @Override
    public User readEntityById(long id) {
        return (User) db.readEntityById(User.class, id);
    }

//    @Override
    public Iterable<User> readEntities(SearchCondition<User> condition) {
        return db.readEntities(User.class, condition);
    }

//    @Override
    public boolean updateEntityById(long id, User entity) {
        return db.updateEntityById(User.class, id, entity);
    }

//    @Override
    public boolean deleteEntityById(long id) {
        return db.deleteEntityById(User.class, id);
    }

//    @Override
    public boolean deleteEntities(SearchCondition<User> condition) {
        return db.deleteEntities(User.class, condition);
    }
}
