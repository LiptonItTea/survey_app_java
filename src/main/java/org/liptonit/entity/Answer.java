package org.liptonit.entity;

public class Answer {
    private long id;
    private String text;
    private long idQuestion;

    public Answer(long id, String text, long idQuestion) {
        this.id = id;
        this.text = text;
        this.idQuestion = idQuestion;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public long getIdQuestion() {
        return idQuestion;
    }
}
