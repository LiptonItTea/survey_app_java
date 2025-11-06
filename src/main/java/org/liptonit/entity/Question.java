package org.liptonit.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Question extends DBEntity{
    private String text;
    private boolean multipleAnswers;
    private long idSurvey;

    public Question(long id, String text, boolean multipleAnswers, long idSurvey) {
        super(id);
        this.text = text;
        this.multipleAnswers = multipleAnswers;
        this.idSurvey = idSurvey;
    }

    public Question(Long id, Question entity) {
        super(id, entity);
        this.text = entity.getText();
        this.multipleAnswers = entity.isMultipleAnswers();
        this.idSurvey = entity.getIdSurvey();
    }

    public Question(ResultSet rs) throws SQLException {
        super(rs);

        this.text = rs.getString("text");
        this.multipleAnswers = rs.getBoolean("multiple_answers");
        this.idSurvey = rs.getLong("id_survey");
    }

    public String getText() {
        return text;
    }

    public boolean isMultipleAnswers() {
        return multipleAnswers;
    }

    public long getIdSurvey() {
        return idSurvey;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Question question = (Question) o;
        return multipleAnswers == question.multipleAnswers && idSurvey == question.idSurvey && Objects.equals(text, question.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text, multipleAnswers, idSurvey);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setMultipleAnswers(boolean multipleAnswers) {
        this.multipleAnswers = multipleAnswers;
    }

    public void setIdSurvey(long idSurvey) {
        this.idSurvey = idSurvey;
    }
}