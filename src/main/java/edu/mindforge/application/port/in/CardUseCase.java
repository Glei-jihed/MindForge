package edu.mindforge.application.port.in;

import edu.mindforge.domain.model.Card;

import java.util.List;

public interface CardUseCase {
    Card createCard(String question, String answer, String tag);
    List<Card> getCardsByTags(List<String> tags);
    List<Card> getCardsForQuiz(String date);
    void answerCard(String cardId, boolean isValid);
}
