package org.liptonit.entity;

import java.util.Objects;

public class QuestionAnswer extends DBEntity{
    private long idCompletedSurvey;
    private long idAnswer;

    public QuestionAnswer(long id, long idCompletedSurvey, long idAnswer) {
        super(id);
        this.idCompletedSurvey = idCompletedSurvey;
        this.idAnswer = idAnswer;
    }

    public QuestionAnswer(Long id, QuestionAnswer entity) {
        super(id, entity);
        this.idCompletedSurvey = entity.getIdCompletedSurvey();
        this.idAnswer = entity.getIdAnswer();
    }

    public long getIdCompletedSurvey() {
        return idCompletedSurvey;
    }

    public long getIdAnswer() {
        return idAnswer;
    }

    public void setIdCompletedSurvey(long idCompletedSurvey) {
        this.idCompletedSurvey = idCompletedSurvey;
    }

    public void setIdAnswer(long idAnswer) {
        this.idAnswer = idAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        QuestionAnswer that = (QuestionAnswer) o;
        return idCompletedSurvey == that.idCompletedSurvey && idAnswer == that.idAnswer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idCompletedSurvey, idAnswer);
    }
}