package edu.mindforge.application;
import edu.mindforge.application.port.out.CardRepositoryPort;
import edu.mindforge.application.service.CardService;
import edu.mindforge.domain.model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CardServiceTest {

    private CardRepositoryPort repositoryPort;
    private CardService cardService;

    @BeforeEach
    public void setup() {
        repositoryPort = Mockito.mock(CardRepositoryPort.class);
        cardService = new CardService(repositoryPort);
    }

    @Test
    public void testCreateCard() {

        Card card = new Card("What is Java?", "A programming language", "Programming");
        when(repositoryPort.save(any(Card.class))).thenReturn(card);


        Card createdCard = cardService.createCard("What is Java?", "A programming language", "Programming");


        assertNotNull(createdCard.getId());
        assertEquals("What is Java?", createdCard.getQuestion());
        verify(repositoryPort, times(1)).save(any(Card.class));
    }

    @Test
    public void testAnswerCard_CardNotFound() {
        String cardId = "non-existing-id";
        when(repositoryPort.findById(cardId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> cardService.answerCard(cardId, true));
    }


}
