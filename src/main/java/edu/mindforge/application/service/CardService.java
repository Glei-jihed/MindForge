package edu.mindforge.application.service;

import edu.mindforge.application.dto.AnswerResponse;
import edu.mindforge.application.port.in.CardUseCase;
import edu.mindforge.application.port.out.CardRepositoryPort;
import edu.mindforge.domain.exception.CardNotFoundException;
import edu.mindforge.domain.model.Card;
import edu.mindforge.domain.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService implements CardUseCase {

    private final CardRepositoryPort cardRepositoryPort;

    public CardService(CardRepositoryPort cardRepositoryPort) {
        this.cardRepositoryPort = cardRepositoryPort;
    }

    @Override
    public Card createCard(String question, String answer, String tag) {
        Card card = new Card(question, answer, tag);
        return cardRepositoryPort.save(card);
    }

    @Override
    public List<Card> getCardsByTags(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return cardRepositoryPort.findAll();
        }
        return cardRepositoryPort.findByTag(tags);
    }

    @Override
    public List<Card> getCardsForQuiz(String date) {
        return cardRepositoryPort.findAll().stream()
                .filter(card -> !card.getCategory().equals(Category.DONE))
                .toList();
    }

    /**
     * Compare la réponse de l'utilisateur avec la réponse attendue.
     * Applique la validation forcée si nécessaire et met à jour la carte.
     *
     * @param cardId          l'identifiant de la carte
     * @param userAnswer      la réponse fournie par l'utilisateur
     * @param forceValidation si true, la réponse est considérée comme correcte
     * @return AnswerResponse contenant le résultat de la comparaison et des détails complémentaires
     */
    @Override
    public AnswerResponse answerCard(String cardId, String userAnswer, Boolean forceValidation) {
        Card card = cardRepositoryPort.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException("Card not found: " + cardId));

        boolean isValid = (userAnswer != null && userAnswer.equalsIgnoreCase(card.getAnswer()));
        String comparisonDetail = "";
        if (Boolean.TRUE.equals(forceValidation)) {
            if (!isValid) {
                comparisonDetail = "Validation forcée : la réponse attendue est \"" + card.getAnswer() +
                        "\", mais vous avez répondu \"" + userAnswer + "\".";
            }
            isValid = true;
        } else if (!isValid) {
            comparisonDetail = "Votre réponse ne correspond pas à la réponse attendue.";
        }

        card.answerQuestion(isValid);
        cardRepositoryPort.save(card);

        return new AnswerResponse(isValid, card.getAnswer(), userAnswer, comparisonDetail);
    }
}
