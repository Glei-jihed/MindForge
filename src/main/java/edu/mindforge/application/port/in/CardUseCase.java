package edu.mindforge.application.port.in;

import edu.mindforge.application.dto.AnswerResponse;
import edu.mindforge.domain.model.Card;

import java.util.List;

public interface CardUseCase {
    Card createCard(String question, String answer, String tag);
    List<Card> getCardsByTags(List<String> tags);
    List<Card> getCardsForQuiz(String date);
    AnswerResponse answerCard(String cardId, String userAnswer, Boolean forceValidation);
}
