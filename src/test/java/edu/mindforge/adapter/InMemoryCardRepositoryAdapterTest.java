package edu.mindforge.adapter;

import edu.mindforge.adapter.out.persistence.InMemoryCardRepositoryAdapter;
import edu.mindforge.application.port.out.CardRepositoryPort;
import edu.mindforge.domain.model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryCardRepositoryAdapterTest {

    private CardRepositoryPort repository;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryCardRepositoryAdapter();
    }

    @Test
    public void testSaveAndFindById() {

        Card card = new Card("What is Java?", "A programming language", "Programming");
        repository.save(card);


        Optional<Card> retrieved = repository.findById(card.getId());
        assertTrue(retrieved.isPresent(), "La carte doit être présente dans le repository");
        assertEquals(card.getQuestion(), retrieved.get().getQuestion(), "La question doit correspondre");
    }

    @Test
    public void testFindAll() {
        // Création et sauvegarde de deux cartes
        Card card1 = new Card("Q1", "A1", "Tag1");
        Card card2 = new Card("Q2", "A2", "Tag2");
        repository.save(card1);
        repository.save(card2);
        List<Card> allCards = repository.findAll();
        assertEquals(2, allCards.size(), "findAll() doit retourner 2 cartes");
    }

    @Test
    public void testFindByTag() {

        Card card1 = new Card("Q1", "A1", "Tag1");
        Card card2 = new Card("Q2", "A2", "Tag2");
        Card card3 = new Card("Q3", "A3", "Tag1");
        repository.save(card1);
        repository.save(card2);
        repository.save(card3);


        List<Card> cardsByTag = repository.findByTag(List.of("Tag1"));
        assertEquals(2, cardsByTag.size(), "Il devrait y avoir 2 cartes avec Tag1");
        cardsByTag.forEach(card -> assertEquals("Tag1", card.getTag(), "Chaque carte doit avoir le tag 'Tag1'"));
    }
}
