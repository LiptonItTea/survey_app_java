package org.liptonit;

import org.liptonit.db.InMemoryDatabase;
import org.liptonit.db.repo.Database;

public class Vars {
    public static Database db = new InMemoryDatabase();
}
