package edu.mindforge.application.service;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class QuizService {

    private LocalDate lastQuizDate = null;

    public void startQuiz(LocalDate today) {
        if (lastQuizDate != null && lastQuizDate.equals(today)) {
            throw new RuntimeException("Vous avez déjà fait un quiz aujourd’hui.");
        }
        // Mémoriser la date de ce quiz
        lastQuizDate = today;

        // Ici, vous pourriez appeler cardUseCase.getCardsForQuiz(...)
        // ou autre logique pour récupérer les cartes du jour
        // ...
    }
}
