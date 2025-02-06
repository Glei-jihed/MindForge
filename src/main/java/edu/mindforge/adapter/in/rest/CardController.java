package edu.mindforge.adapter.in.rest;

import edu.mindforge.application.dto.AnswerResponse;
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

    @GetMapping
    public ResponseEntity<List<Card>> getCards(@RequestParam(value = "tags", required = false) List<String> tags) {
        List<Card> cards = cardUseCase.getCardsByTags(tags);
        return ResponseEntity.ok(cards);
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody CardUserData cardUserData) {
        if (cardUserData.getQuestion() == null || cardUserData.getAnswer() == null) {
            return ResponseEntity.badRequest().build();
        }
        Card createdCard = cardUseCase.createCard(cardUserData.getQuestion(), cardUserData.getAnswer(), cardUserData.getTag());
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @GetMapping("/quizz")
    public ResponseEntity<List<Card>> getCardsForQuiz(@RequestParam(value = "date", required = false) String date) {
        List<Card> cards = cardUseCase.getCardsForQuiz(date);
        return ResponseEntity.ok(cards);
    }

    /**
     * PATCH /cards/{cardId}/answer
     * Met à jour la réponse d'une carte en comparant la réponse de l'utilisateur.
     */
    @PatchMapping("/{cardId}/answer")
    public ResponseEntity<AnswerResponse> answerCard(@PathVariable String cardId, @RequestBody AnswerData answerData) {
        if (answerData.getUserAnswer() == null) {
            return ResponseEntity.badRequest().build();
        }
        AnswerResponse response = cardUseCase.answerCard(cardId, answerData.getUserAnswer(), answerData.getForceValidation());
        if (!response.isValid()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    public static class CardUserData {
        private String question;
        private String answer;
        private String tag;

        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
        public String getAnswer() { return answer; }
        public void setAnswer(String answer) { this.answer = answer; }
        public String getTag() { return tag; }
        public void setTag(String tag) { this.tag = tag; }
    }


    public static class AnswerData {
        private String userAnswer;
        private Boolean forceValidation;
        public String getUserAnswer() { return userAnswer; }
        public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }
        public Boolean getForceValidation() { return forceValidation; }
        public void setForceValidation(Boolean forceValidation) { this.forceValidation = forceValidation; }
    }
}
