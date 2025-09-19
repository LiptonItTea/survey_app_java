package org.liptonit;


import org.liptonit.repo.InMemoryDatabase;

public class Main {
    public static void main(String[] args) {
        InMemoryDatabase db = new InMemoryDatabase();

        db.readEntity(u -> u.getId() == 10);
    }
}