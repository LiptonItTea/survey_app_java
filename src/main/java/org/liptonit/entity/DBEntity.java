package org.liptonit.entity;

import java.util.Objects;

public abstract class DBEntity {
    private final long id;

    public DBEntity(long id) {this.id = id;}

    public DBEntity(Long id, DBEntity entity) {
        this.id = id;
    }

    public long getId() {return id;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DBEntity dbEntity = (DBEntity) o;
        return id == dbEntity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
