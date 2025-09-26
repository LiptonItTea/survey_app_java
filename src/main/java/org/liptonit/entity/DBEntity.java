package org.liptonit.entity;

public class DBEntity {
    private long id;

    public DBEntity(long id) {this.id = id;}

    public DBEntity(DBEntity entity) {
        this.id = entity.getId();
    }

    public long getId() {return id;}
}
