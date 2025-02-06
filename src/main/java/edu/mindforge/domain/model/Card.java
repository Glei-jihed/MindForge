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

    public Card(String question, String answer, String tag) {
        this.id = UUID.randomUUID().toString();
        this.question = question;
        this.answer = answer;
        this.tag = tag;
        this.category = Category.FIRST;
        this.lastReviewDate = null;
        this.nextReviewDate = LocalDate.now(); // initialisation par défaut
    }

    // Getters
    public String getId() { return id; }
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public String getTag() { return tag; }
    public Category getCategory() { return category; }
    public LocalDate getLastReviewDate() { return lastReviewDate; }
    public LocalDate getNextReviewDate() { return nextReviewDate; }


    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Met à jour la fiche en fonction de la réponse de l'utilisateur.
     * Si la réponse est correcte, la carte passe à la catégorie suivante et la prochaine date de révision est définie selon l'intervalle:
     * - FIRST -> SECOND: 2 jours
     * - SECOND -> THIRD: 4 jours
     * - THIRD -> FOURTH: 8 jours
     * - FOURTH -> FIFTH: 16 jours
     * - FIFTH -> SIXTH: 32 jours
     * - SIXTH -> SEVENTH: 64 jours
     * - SEVENTH -> DONE: aucune révision
     * Si la réponse est incorrecte, la carte retourne en FIRST et la prochaine révision est dans 1 jour.
     *
     * @param isValid true si la réponse est correcte, false sinon.
     */
    public void answerQuestion(boolean isValid) {
        LocalDate today = LocalDate.now();
        this.lastReviewDate = today;
        if (isValid) {
            switch (this.category) {
                case FIRST:
                    this.category = Category.SECOND;
                    this.nextReviewDate = today.plusDays(2);
                    break;
                case SECOND:
                    this.category = Category.THIRD;
                    this.nextReviewDate = today.plusDays(4);
                    break;
                case THIRD:
                    this.category = Category.FOURTH;
                    this.nextReviewDate = today.plusDays(8);
                    break;
                case FOURTH:
                    this.category = Category.FIFTH;
                    this.nextReviewDate = today.plusDays(16);
                    break;
                case FIFTH:
                    this.category = Category.SIXTH;
                    this.nextReviewDate = today.plusDays(32);
                    break;
                case SIXTH:
                    this.category = Category.SEVENTH;
                    this.nextReviewDate = today.plusDays(64);
                    break;
                case SEVENTH:
                    this.category = Category.DONE;
                    this.nextReviewDate = null;
                    return;
                default:
                    break;
            }
        } else {
            this.category = Category.FIRST;
            this.nextReviewDate = today.plusDays(1);
        }
    }
}
