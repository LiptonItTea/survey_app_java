package org.liptonit;

import org.liptonit.db.InMemoryDatabase;
import org.liptonit.db.repo.Database;
import org.liptonit.db.repo.UserRepository;

public class Vars {
    public static Database db = new InMemoryDatabase();
    public static UserRepository userRepository = new UserRepository(db);
}
