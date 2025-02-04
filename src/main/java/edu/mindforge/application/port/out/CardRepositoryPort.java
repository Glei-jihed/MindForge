package edu.mindforge.application.port.out;
import edu.mindforge.domain.model.Card;
import java.util.List;
import java.util.Optional;

public interface CardRepositoryPort {
    Card save(Card card);
    Optional<Card> findById(String id);
    List<Card> findAll();
    List<Card> findByTag(List<String> tags);
}
