package org.liptonit.entity;

public class QuestionAnswer extends DBEntity{
    private long id;
    private long idCompletedSurvey;
    private long idAnswer;

    public QuestionAnswer(long id, long idCompletedSurvey, long idAnswer) {
        super(id);
        this.idCompletedSurvey = idCompletedSurvey;
        this.idAnswer = idAnswer;
    }

    public long getIdCompletedSurvey() {
        return idCompletedSurvey;
    }

    public long getIdAnswer() {
        return idAnswer;
    }
}
