package org.liptonit.entity;

public class CompletedSurvey extends DBEntity{
    private long idUser;

    public CompletedSurvey(long id, long idUser) {
        super(id);
        this.idUser = idUser;
    }

    public long getIdUser() {
        return idUser;
    }
}
