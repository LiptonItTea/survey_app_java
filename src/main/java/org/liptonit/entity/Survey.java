package org.liptonit.entity;

public class Survey {
    private long id;
    private String name;
    private String description;
    private long idUserCreator;

    public Survey(long id, String name, String description, long idUserCreator) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.idUserCreator = idUserCreator;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getIdUserCreator() {
        return idUserCreator;
    }
}
