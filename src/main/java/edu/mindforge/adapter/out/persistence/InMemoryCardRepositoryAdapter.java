package edu.mindforge.adapter.out.persistence;

import edu.mindforge.application.port.out.CardRepositoryPort;
import edu.mindforge.domain.model.Card;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryCardRepositoryAdapter implements CardRepositoryPort {

    private final Map<String, Card> cardStore = new ConcurrentHashMap<>();

    @Override
    public Card save(Card card) {
        cardStore.put(card.getId(), card);
        return card;
    }

    @Override
    public Optional<Card> findById(String id) {
        return Optional.ofNullable(cardStore.get(id));
    }

    @Override
    public List<Card> findAll() {
        return new ArrayList<>(cardStore.values());
    }

    @Override
    public List<Card> findByTag(List<String> tags) {
        List<Card> result = new ArrayList<>();
        for (Card card : cardStore.values()) {
            if (card.getTag() != null && tags.contains(card.getTag())) {
                result.add(card);
            }
        }
        return result;
    }
}
