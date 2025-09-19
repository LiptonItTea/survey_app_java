package org.liptonit.entity;

public class CompletedSurvey {
    private long id;
    private long idUser;

    public CompletedSurvey(long id, long idUser) {
        this.id = id;
        this.idUser = idUser;
    }

    public long getId() {
        return id;
    }

    public long getIdUser() {
        return idUser;
    }
}
