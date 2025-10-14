package org.liptonit.entity;

import java.util.Objects;

public class CompletedSurvey extends DBEntity{
    private long idUser;

    public CompletedSurvey(long id, long idUser) {
        super(id);
        this.idUser = idUser;
    }

    public CompletedSurvey(Long id, CompletedSurvey entity) {
        super(id, entity);
        this.idUser = entity.getIdUser();
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CompletedSurvey that = (CompletedSurvey) o;
        return idUser == that.idUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idUser);
    }
}