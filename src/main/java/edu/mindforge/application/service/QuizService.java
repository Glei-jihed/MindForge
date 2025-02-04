package edu.mindforge.application.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;

/**
 * Service permettant de gérer la contrainte d’un quiz par jour.
 */
@Service
public class QuizService {

    // Stocke la date du dernier quiz réalisé.
    private LocalDate lastQuizDate = null;

    /**
     * Démarre un quiz pour la date spécifiée.
     * Si un quiz a déjà été réalisé aujourd'hui, une exception est levée.
     *
     * @param today la date du quiz
     * @throws RuntimeException si un quiz a déjà été fait aujourd’hui
     */
    public void startQuiz(LocalDate today) {
        if (lastQuizDate != null && lastQuizDate.equals(today)) {
            throw new RuntimeException("Vous avez déjà fait un quiz aujourd’hui.");
        }
        // Enregistre la date de ce quiz
        lastQuizDate = today;
        // Ici, vous pourriez appeler cardUseCase.getCardsForQuiz(...)
        // ou implémenter une logique pour récupérer les cartes du jour.
    }

    /**
     * Retourne la date du dernier quiz réalisé.
     *
     * @return la date du dernier quiz ou null s'il n'y en a pas
     */
    public LocalDate getLastQuizDate() {
        return lastQuizDate;
    }
}
