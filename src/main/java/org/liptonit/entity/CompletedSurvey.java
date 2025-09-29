package org.liptonit.entity;

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
}
