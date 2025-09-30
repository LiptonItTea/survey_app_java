package org.liptonit.entity;

public abstract class DBEntity {
    private final long id;

    public DBEntity(long id) {this.id = id;}

    public DBEntity(Long id, DBEntity entity) {
        this.id = id;
    }

    public long getId() {return id;}
}
