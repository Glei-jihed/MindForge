package edu.mindforge.adapter.in.rest;
import edu.mindforge.application.port.in.CardUseCase;
import edu.mindforge.domain.model.Card;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardUseCase cardUseCase;

    public CardController(CardUseCase cardUseCase) {
        this.cardUseCase = cardUseCase;
    }

    /**
     * GET /cards?tags=tag1,tag2
     * Récupère toutes les cartes ou celles correspondant aux tags fournis.
     */
    @GetMapping
    public ResponseEntity<List<Card>> getCards(@RequestParam(value = "tags", required = false) List<String> tags) {
        List<Card> cards = cardUseCase.getCardsByTags(tags);
        return ResponseEntity.ok(cards);
    }

    /**
     * POST /cards
     * Crée une nouvelle carte.
     */
    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody CardUserData cardUserData) {
        if (cardUserData.getQuestion() == null || cardUserData.getAnswer() == null) {
            return ResponseEntity.badRequest().build();
        }
        Card createdCard = cardUseCase.createCard(cardUserData.getQuestion(), cardUserData.getAnswer(), cardUserData.getTag());
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    /**
     * GET /cards/quizz?date=yyyy-MM-dd
     * Récupère les cartes à réviser pour le quiz à une date donnée.
     */
    @GetMapping("/quizz")
    public ResponseEntity<List<Card>> getCardsForQuiz(@RequestParam(value = "date", required = false) String date) {
        List<Card> cards = cardUseCase.getCardsForQuiz(date);
        return ResponseEntity.ok(cards);
    }

    /**
     * PATCH /cards/{cardId}/answer
     * Enregistre la réponse à une carte.
     */
    @PatchMapping("/{cardId}/answer")
    public ResponseEntity<Void> answerCard(@PathVariable String cardId, @RequestBody AnswerData answerData) {
        if (answerData.getIsValid() == null) {
            return ResponseEntity.badRequest().build();
        }
        cardUseCase.answerCard(cardId, answerData.getIsValid());
        return ResponseEntity.noContent().build();
    }



    public static class CardUserData {
        private String question;
        private String answer;
        private String tag;

        public String getQuestion() {
            return question;
        }
        public void setQuestion(String question) {
            this.question = question;
        }
        public String getAnswer() {
            return answer;
        }
        public void setAnswer(String answer) {
            this.answer = answer;
        }
        public String getTag() {
            return tag;
        }
        public void setTag(String tag) {
            this.tag = tag;
        }
    }

    public static class AnswerData {
        private Boolean isValid;

        public Boolean getIsValid() {
            return isValid;
        }
        public void setIsValid(Boolean isValid) {
            this.isValid = isValid;
        }
    }
}