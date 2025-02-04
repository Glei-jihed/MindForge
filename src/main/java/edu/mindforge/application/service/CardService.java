package edu.mindforge.application.service;
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

    @Override
    public void answerCard(String cardId, boolean isValid) {
        Card card = cardRepositoryPort.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException("Card not found: " + cardId));
        card.answerQuestion(isValid);
        cardRepositoryPort.save(card);
    }
}
