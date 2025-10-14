package org.liptonit.entity;

import java.util.Objects;

public class Survey extends DBEntity{
    private String name;
    private String description;
    private long idUserCreator;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getIdUserCreator() {
        return idUserCreator;
    }

    public void setIdUserCreator(long idUserCreator) {
        this.idUserCreator = idUserCreator;
    }

    @Override
    public String toString() {
        return "ID: " + getId() +
                "\nName: " + this.name +
                "\nDescription: " + this.description +
                "\nId user creator: " + this.idUserCreator;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Survey survey = (Survey) o;
        return idUserCreator == survey.idUserCreator && Objects.equals(name, survey.name) && Objects.equals(description, survey.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, idUserCreator);
    }
}
