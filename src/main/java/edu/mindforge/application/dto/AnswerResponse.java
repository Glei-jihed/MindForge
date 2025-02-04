package edu.mindforge.application.dto;

public class AnswerResponse {
    private boolean valid;
    private String expectedAnswer;
    private String userAnswer;

    public AnswerResponse(boolean valid, String expectedAnswer, String userAnswer) {
        this.valid = valid;
        this.expectedAnswer = expectedAnswer;
        this.userAnswer = userAnswer;
    }

    public boolean isValid() {
        return valid;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    public String getUserAnswer() {
        return userAnswer;
    }
}
