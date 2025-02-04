# **MindForge - Dockerized Application**

**Utilisation de Docker et lancement du projet :**
Pour lancer le projet, assurez-vous d'avoir Docker et Docker Compose installés. Ensuite, depuis la racine du projet, exécutez :
```bash
docker-compose up --build -d
Cela démarrera l'application, MySQL et phpMyAdmin. L'application sera accessible sur http://localhost:8080 et phpMyAdmin sur http://localhost:8081.

Table des Matières
Présentation
Technologies Utilisées
Architecture
Installation et Exécution
Exécution via Docker Compose
Exécution en Local (hors Docker)
API et Endpoints
Tests
Tests Unitaires et d'Intégration
Tests End-to-End
Historique TDD et Git
Documentation Supplémentaire
Présentation
MindForge est une application web d'aide à l'apprentissage basée sur le système de Leitner, mettant en œuvre le principe de la répétition espacée et l'auto-évaluation. Le projet est développé selon les bonnes pratiques (SOLID, Domain-Driven Design, Architecture Hexagonale) et en suivant une approche Test-Driven Development (TDD).

Technologies Utilisées
Java 17
Spring Boot 3.4.2
Maven
MySQL 8.0 (via Docker)
phpMyAdmin (pour la gestion de la base de données)
Docker & Docker Compose
JUnit & Mockito (pour les tests unitaires et d'intégration)
Cucumber / Gherkin (pour les tests end-to-end, optionnel)
Architecture
Le projet suit une architecture hexagonale (Ports & Adapters) :

Domaine : Contient les entités (Card, Category) et la logique métier.
Application : Contient les cas d'utilisation et les ports d'entrée (CardUseCase) et de sortie (CardRepositoryPort), ainsi que le service CardService.
Adaptateurs : Comprend l'adaptateur REST (CardController) et l'adaptateur de persistance (InMemoryCardRepositoryAdapter).
Configuration : Assemblage des beans via BeanConfiguration.
Installation et Exécution
Exécution via Docker Compose
Générer l'artefact JAR :

bash
Copier
mvn clean package
Le fichier JAR sera généré dans le dossier target sous le nom mindforge-0.0.1-SNAPSHOT.jar.

Lancer les containers Docker :

bash
Copier
docker-compose up --build -d
Cela démarrera :

Un container MySQL (port interne 3306, mappé sur 3307 sur l'hôte)
Un container phpMyAdmin (accessible sur http://localhost:8081)
Un container MindForge App (accessible sur http://localhost:8080)
Exécution en Local (hors Docker)
Si vous souhaitez exécuter l'application en local (hors Docker), assurez-vous d'avoir une instance MySQL fonctionnelle sur votre machine et modifiez le fichier application.yml pour utiliser :

yaml
Copier
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/mindforge_db?useSSL=false&serverTimezone=UTC
Ensuite, lancez l'application via Maven :

bash
Copier
mvn spring-boot:run
API et Endpoints
L'API est conforme au Swagger fourni. Voici quelques exemples de requêtes à tester avec Postman :

1. Créer une carte
URL :

bash
Copier
POST http://localhost:8080/cards
Body (JSON) :

json
Copier
{
  "question": "What is pair programming?",
  "answer": "A practice to work in pair on same computer.",
  "tag": "Teamwork"
}
Réponse attendue :

Statut 201 Created avec l'objet Card (incluant id et category initialisée à "FIRST").
2. Récupérer des cartes
URL sans filtre :

bash
Copier
GET http://localhost:8080/cards
URL avec filtre par tags :

bash
Copier
GET http://localhost:8080/cards?tags=Teamwork,Programming
Réponse attendue :

Statut 200 OK avec un tableau JSON de cartes.
3. Récupérer les cartes pour le quiz
URL :

bash
Copier
GET http://localhost:8080/cards/quizz?date=2023-11-03
Réponse attendue :

Statut 200 OK avec un tableau JSON des cartes à réviser.
4. Enregistrer une réponse à une carte
URL (remplacer {cardId} par l'identifiant de la carte) :

bash
Copier
PATCH http://localhost:8080/cards/{cardId}/answer
Body (JSON) :

json
Copier
{
  "isValid": true
}
Réponse attendue :

Statut 204 No Content.
Tests
Tests Unitaires et d'Intégration
Tests Unitaires :
Vérifient la logique métier dans les couches Domaine et Application (ex. : Card.answerQuestion() et CardService avec Mockito).

bash
Copier
mvn test
Tests d'Intégration :
Testent les endpoints REST via MockMvc (CardControllerTest).

Tests End-to-End
Scénario End-to-End :
Des scénarios sont définis en Gherkin (par exemple, dans src/test/resources/features/create_card.feature) et exécutés avec Cucumber.
Exécution des tests end-to-end :
bash
Copier
mvn test -Dcucumber.options="--plugin pretty --plugin html:target/cucumber-report.html"
Le rapport sera disponible dans target/cucumber-report.html.
Historique TDD et Git
Le projet a été développé en suivant une approche TDD avec des commits distincts pour chaque étape fonctionnelle. Voici quelques exemples de messages de commit utilisés :

feat(domain): add Category enum with FIRST to DONE
test(domain): add tests for Card entity initialization and answerQuestion behavior
feat(domain): implement Card entity with answerQuestion logic
feat(application): add CardUseCase and CardRepositoryPort interfaces
test(application): add tests for CardService createCard and answerCard
feat(application): implement CardService based on CardRepositoryPort
test(persistence): add tests for InMemoryCardRepositoryAdapter methods
feat(persistence): implement InMemoryCardRepositoryAdapter using ConcurrentHashMap
test(rest): add integration tests for CardController endpoints
feat(rest): implement CardController exposing REST API endpoints for card operations
feat(configuration): add BeanConfiguration to wire CardRepositoryPort and CardService
test(e2e): add Gherkin scenario for card creation
feat(e2e): implement end-to-end test for card creation
Documentation Supplémentaire
Pour plus d'informations sur l'architecture hexagonale et le DDD, consultez les diagrammes dans le dossier docs (si créés) ou les sections explicatives de ce README.

Conclusion
MindForge est une application d'aide à l'apprentissage conçue selon les principes SOLID, DDD et l'architecture hexagonale, développée en TDD. Suivez les instructions ci-dessus pour installer, lancer et tester l'application. N'hésitez pas à consulter l'historique Git pour suivre l'évolution du projet et à ouvrir une issue pour toute question ou amélioration.

Bonne utilisation et bon développement !
