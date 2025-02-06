package edu.mindforge.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class QuizServiceTest {

    private QuizService quizService;

    @BeforeEach
    public void setUp() {
        quizService = new QuizService();
    }

    @Test
    public void testStartQuiz_FirstQuizSucceeds() {
        LocalDate today = LocalDate.now();

        assertDoesNotThrow(() -> quizService.startQuiz(today));
        assertEquals(today, quizService.getLastQuizDate(), "La date du dernier quiz doit être enregistrée");
    }

    @Test
    public void testStartQuiz_SecondQuizSameDayFails() {
        LocalDate today = LocalDate.now();
        quizService.startQuiz(today);
        // Un second quiz le même jour doit lever une exception
        Exception exception = assertThrows(RuntimeException.class, () -> quizService.startQuiz(today));
        assertEquals("Vous avez déjà fait un quiz aujourd’hui.", exception.getMessage());
    }

    @Test
    public void testStartQuiz_QuizOnDifferentDaysSucceeds() {
        LocalDate day1 = LocalDate.now();
        LocalDate day2 = day1.plusDays(1);
        quizService.startQuiz(day1);
        // Un quiz le jour suivant doit réussir
        assertDoesNotThrow(() -> quizService.startQuiz(day2));
        assertEquals(day2, quizService.getLastQuizDate(), "La date du dernier quiz doit être mise à jour pour le nouveau jour");
    }
}
