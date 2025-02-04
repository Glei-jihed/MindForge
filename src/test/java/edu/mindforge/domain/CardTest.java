package edu.mindforge.domain;
import edu.mindforge.domain.model.Card;
import edu.mindforge.domain.model.Category;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void testCardInitialization() {
        Card card = new Card("What is Java?", "A programming language", "Programming");
        assertEquals(Category.FIRST, card.getCategory(), "La carte doit être initialisée en FIRST");
        assertNotNull(card.getNextReviewDate(), "La date de prochaine révision doit être définie");
    }

    @Test
    public void testAnswerQuestion_CorrectResponse() {
        Card card = new Card("What is Java?", "A programming language", "Programming");
        LocalDate previousNextReview = card.getNextReviewDate();
        card.answerQuestion(true);
        // Exemple : la carte passe à SECOND (selon votre logique)
        assertEquals(Category.SECOND, card.getCategory(), "La carte doit passer à la catégorie suivante pour une réponse correcte");
        assertNotEquals(previousNextReview, card.getNextReviewDate(), "La date de prochaine révision doit être mise à jour");
    }

    @Test
    public void testAnswerQuestion_IncorrectResponse() {
        Card card = new Card("What is Java?", "A programming language", "Programming");
        // Passer en catégorie supérieure pour simuler un quiz réussi
        card.answerQuestion(true);
        // Maintenant simuler une mauvaise réponse
        card.answerQuestion(false);
        // La carte doit revenir en FIRST
        assertEquals(Category.FIRST, card.getCategory(), "La carte doit revenir à FIRST en cas de mauvaise réponse");
    }
}