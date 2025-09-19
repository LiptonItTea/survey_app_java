package org.liptonit.entity;

public class Question {
    private long id;
    private String text;
    private boolean multipleAnswers;
    private long idSurvey;

    public Question(long id, String text, boolean multipleAnswers, long idSurvey) {
        this.id = id;
        this.text = text;
        this.multipleAnswers = multipleAnswers;
        this.idSurvey = idSurvey;
    }

    public long getId() {
        return id;
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
}
