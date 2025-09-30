package org.liptonit.entity;

public class Question extends DBEntity{
    private final String text;
    private final boolean multipleAnswers;
    private final long idSurvey;

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
