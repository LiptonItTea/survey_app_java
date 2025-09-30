package org.liptonit.entity;

public class Survey extends DBEntity{
    private final String name;
    private final String description;
    private final long idUserCreator;

    public Survey(long id, String name, String description, long idUserCreator) {
        super(id);
        this.name = name;
        this.description = description;
        this.idUserCreator = idUserCreator;
    }

    public Survey(Long id, Survey entity) {
        super(id, entity);
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.idUserCreator = entity.getIdUserCreator();
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

    @Override
    public String toString() {
        return "ID: " + getId() +
                "\nName: " + this.name +
                "\nDescription: " + this.description +
                "\nId user creator: " + this.idUserCreator;
    }
}
