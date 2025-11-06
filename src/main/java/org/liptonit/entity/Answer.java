package org.liptonit.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Answer extends DBEntity{
    private String text;
    private long idQuestion;

    public Answer(long id, String text, long idQuestion) {
        super(id);
        this.text = text;
        this.idQuestion = idQuestion;
    }

    public Answer(Long id, Answer entity) {
        super(id, entity);
        this.text = entity.getText();
        this.idQuestion = entity.getIdQuestion();
    }

    public Answer(ResultSet rs) throws SQLException {
        super(rs);

        this.text = rs.getString("text");
        this.idQuestion = rs.getLong("id_question");
    }

    public String getText() {
        return text;
    }

    public long getIdQuestion() {
        return idQuestion;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIdQuestion(long idQuestion) {
        this.idQuestion = idQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Answer answer = (Answer) o;
        return idQuestion == answer.idQuestion && Objects.equals(text, answer.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text, idQuestion);
    }
}