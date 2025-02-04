package edu.mindforge.domain.model;



import java.time.LocalDate;
import java.util.UUID;

public class Card {

    private String id;
    private String question;
    private String answer;
    private String tag;
    private Category category;
    private LocalDate lastReviewDate;
    private LocalDate nextReviewDate;

    // Constructeur
    public Card(String question, String answer, String tag) {
        this.id = UUID.randomUUID().toString();
        this.question = question;
        this.answer = answer;
        this.tag = tag;
        this.category = Category.FIRST;
        this.lastReviewDate = null;
        this.nextReviewDate = LocalDate.now(); // Vous pouvez ajuster la logique ici
    }

    // Getters
    public String getId() { return id; }
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public String getTag() { return tag; }
    public Category getCategory() { return category; }
    public LocalDate getLastReviewDate() { return lastReviewDate; }
    public LocalDate getNextReviewDate() { return nextReviewDate; }

    /**
     * Met à jour la fiche en fonction de la réponse de l'utilisateur.
     * Pour une réponse correcte, la carte passe à la catégorie suivante (ou DONE si elle était en SEVENTH).
     * Pour une réponse incorrecte, la carte retourne à la catégorie FIRST.
     *
     * @param isValid true si la réponse est correcte, false sinon.
     */
    public void answerQuestion(boolean isValid) {
        LocalDate today = LocalDate.now();
        this.lastReviewDate = today;
        if (isValid) {
            // Exemple de logique : si la carte est FIRST, passe à SECOND, etc.
            // Vous pouvez ajuster les intervalles et la logique de transition selon votre besoin.
            switch (this.category) {
                case FIRST:
                    this.category = Category.SECOND;
                    break;
                case SECOND:
                    this.category = Category.THIRD;
                    break;
                case THIRD:
                    this.category = Category.FOURTH;
                    break;
                case FOURTH:
                    this.category = Category.FIFTH;
                    break;
                case FIFTH:
                    this.category = Category.SIXTH;
                    break;
                case SIXTH:
                    this.category = Category.SEVENTH;
                    break;
                case SEVENTH:
                    this.category = Category.DONE;
                    this.nextReviewDate = null;
                    return;
                default:
                    break;
            }
            // Exemple : mise à jour de la date de prochaine révision (ici, ajout d'un jour pour simplifier)
            this.nextReviewDate = today.plusDays(1);
        } else {
            // Pour une mauvaise réponse, la carte retourne à FIRST
            this.category = Category.FIRST;
            this.nextReviewDate = today.plusDays(1);
        }
    }
}

