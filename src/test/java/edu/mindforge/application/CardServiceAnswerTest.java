package edu.mindforge.application;

import edu.mindforge.application.dto.AnswerResponse;
import edu.mindforge.application.port.out.CardRepositoryPort;
import edu.mindforge.application.service.CardService;
import edu.mindforge.domain.exception.CardNotFoundException;
import edu.mindforge.domain.model.Card;
import edu.mindforge.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CardServiceAnswerTest {

    private CardRepositoryPort repositoryPort;
    private CardService cardService;

    @BeforeEach
    public void setup() {
        repositoryPort = Mockito.mock(CardRepositoryPort.class);
        cardService = new CardService(repositoryPort);
    }

    @Test
    public void testAnswerCard_CorrectAnswer() {

        Card card = new Card("What is Java?", "A programming language", "Programming");
        when(repositoryPort.findById(anyString())).thenReturn(Optional.of(card));
        when(repositoryPort.save(any(Card.class))).thenReturn(card);


        AnswerResponse response = cardService.answerCard(card.getId(), "A programming language", false);


        assertTrue(response.isValid(), "La réponse doit être considérée comme valide");
        assertEquals("A programming language", response.getExpectedAnswer());
        assertEquals("A programming language", response.getUserAnswer());
    }

    @Test
    public void testAnswerCard_IncorrectAnswer() {

        Card card = new Card("What is Java?", "A programming language", "Programming");
        when(repositoryPort.findById(anyString())).thenReturn(Optional.of(card));
        when(repositoryPort.save(any(Card.class))).thenReturn(card);

        AnswerResponse response = cardService.answerCard(card.getId(), "Wrong answer", false);


        assertFalse(response.isValid(), "La réponse doit être considérée comme incorrecte");
        assertEquals("A programming language", response.getExpectedAnswer());
        assertEquals("Wrong answer", response.getUserAnswer());
    }

    @Test
    public void testAnswerCard_ForceValidation() {

        Card card = new Card("What is Java?", "A programming language", "Programming");
        when(repositoryPort.findById(anyString())).thenReturn(Optional.of(card));
        when(repositoryPort.save(any(Card.class))).thenReturn(card);


        AnswerResponse response = cardService.answerCard(card.getId(), "Wrong answer", true);


        assertTrue(response.isValid(), "La validation forcée doit considérer la réponse comme valide");
        assertEquals("A programming language", response.getExpectedAnswer());
        assertEquals("Wrong answer", response.getUserAnswer());
    }

    @Test
    public void testAnswerCard_CardNotFound() {
        when(repositoryPort.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(CardNotFoundException.class, () -> {
            cardService.answerCard("non-existent-id", "Any answer", false);
        });
    }
}
