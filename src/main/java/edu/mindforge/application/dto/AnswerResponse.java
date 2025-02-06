package edu.mindforge.application.dto;

public class AnswerResponse {
    private boolean valid;
    private String expectedAnswer;
    private String userAnswer;
    private String comparisonDetail; // Information complémentaire

    public AnswerResponse(boolean valid, String expectedAnswer, String userAnswer, String comparisonDetail) {
        this.valid = valid;
        this.expectedAnswer = expectedAnswer;
        this.userAnswer = userAnswer;
        this.comparisonDetail = comparisonDetail;
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

    public String getComparisonDetail() {
        return comparisonDetail;
    }
}
