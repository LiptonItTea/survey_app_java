package org.liptonit.entity;

public class QuestionAnswer {
    private long id;
    private long idCompletedSurvey;
    private long idAnswer;

    public QuestionAnswer(long id, long idCompletedSurvey, long idAnswer) {
        this.id = id;
        this.idCompletedSurvey = idCompletedSurvey;
        this.idAnswer = idAnswer;
    }

    public long getId() {
        return id;
    }

    public long getIdCompletedSurvey() {
        return idCompletedSurvey;
    }

    public long getIdAnswer() {
        return idAnswer;
    }
}
