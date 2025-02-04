package edu.mindforge.configuration;

import edu.mindforge.application.port.out.CardRepositoryPort;
import edu.mindforge.adapter.out.persistence.InMemoryCardRepositoryAdapter;
import edu.mindforge.application.service.CardService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CardRepositoryPort cardRepositoryPort() {
        return new InMemoryCardRepositoryAdapter();
    }

    @Bean
    public CardService cardService() {
        return new CardService(cardRepositoryPort());
    }
}
