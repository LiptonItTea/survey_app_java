package org.liptonit;


import org.liptonit.cli.Menu;
import org.liptonit.db.repo.Database;
import org.liptonit.entity.User;
import org.liptonit.db.InMemoryDatabase;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Menu.run();
    }
}