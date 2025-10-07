package org.liptonit.entity;

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
}