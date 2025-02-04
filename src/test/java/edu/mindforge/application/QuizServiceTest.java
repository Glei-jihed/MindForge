package edu.mindforge.application;
import edu.mindforge.application.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class QuizServiceTest {

    private QuizService quizService;

    @BeforeEach
    public void setup() {
        quizService = new QuizService();
    }

    @Test
    public void testCannotTakeTwoQuizzesSameDay() {

        LocalDate today = LocalDate.of(2025, 2, 10);
        quizService.startQuiz(today);


        assertThrows(RuntimeException.class, () -> quizService.startQuiz(today));
    }

    @Test
    public void testTakeQuizOnDifferentDays() {

        LocalDate feb10 = LocalDate.of(2025, 2, 10);
        quizService.startQuiz(feb10);


        LocalDate feb11 = LocalDate.of(2025, 2, 11);
        assertDoesNotThrow(() -> quizService.startQuiz(feb11));
    }
}
