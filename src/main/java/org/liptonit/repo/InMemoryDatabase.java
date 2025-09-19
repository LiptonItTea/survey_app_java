package org.liptonit.repo;

import org.liptonit.entity.User;
import org.liptonit.util.SearchCondition;

import java.util.Map;

public class InMemoryDatabase implements UserRepository {
    private Map<Integer, User> userRepo;

    @Override
    public void createEntity(User entity) {

    }

    @Override
    public User readEntity(SearchCondition<User> condition) {
        for (Map.Entry e : userRepo.entrySet()) {
            User u = (User) e;

            if (condition.select(u))
                return u;
        }
        return null;
    }
}
