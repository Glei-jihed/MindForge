package edu.mindforge.adapter;

import edu.mindforge.adapter.in.rest.CardController;
import edu.mindforge.application.port.in.CardUseCase;
import edu.mindforge.domain.model.Card;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CardController.class)
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CardUseCase cardUseCase;

    @Test
    public void testGetCards_ReturnsEmptyList() throws Exception {
        when(cardUseCase.getCardsByTags(Mockito.anyList())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/cards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testCreateCard_ReturnsCreatedCard() throws Exception {
        Card card = new Card("What is Java?", "A programming language", "Programming");
        when(cardUseCase.createCard(any(), any(), any())).thenReturn(card);

        String requestBody = "{\"question\": \"What is Java?\", \"answer\": \"A programming language\", \"tag\": \"Programming\"}";

        mockMvc.perform(post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.question").value("What is Java?"));
    }


}