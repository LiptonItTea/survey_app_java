package org.liptonit.entity;

public class CompletedSurvey extends DBEntity{
    private long idUser;

    public CompletedSurvey(long id, long idUser) {
        super(id);
        this.idUser = idUser;
    }

    public CompletedSurvey(CompletedSurvey entity) {
        super(entity);
        this.idUser = entity.getIdUser();
    }

    public long getIdUser() {
        return idUser;
    }
}
