package com.hackerrank.github.presenter.config;

import com.hackerrank.github.core.entities.ActorRepository;
import com.hackerrank.github.core.entities.EventRepository;
import com.hackerrank.github.core.usecases.actor.GetActorsUseCase;
import com.hackerrank.github.core.usecases.actor.GetActorsStreakUseCase;
import com.hackerrank.github.core.usecases.actor.UpdateActorUseCase;
import com.hackerrank.github.core.usecases.event.CreateEventUseCase;
import com.hackerrank.github.core.usecases.event.DeleteEventsUseCase;
import com.hackerrank.github.core.usecases.event.GetActorEventsUseCase;
import com.hackerrank.github.core.usecases.event.GetEventsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module {
    @Bean
    public CreateEventUseCase createEventUseCase(EventRepository eventRepository, ActorRepository actorRepository) {
        return new CreateEventUseCase(eventRepository, actorRepository);
    }

    @Bean
    public DeleteEventsUseCase deleteAllEventsUseCase(EventRepository repository) {
        return new DeleteEventsUseCase(repository);
    }

    @Bean
    public GetEventsUseCase getAllEventsUseCase(EventRepository repository) {
        return new GetEventsUseCase(repository);
    }

    @Bean
    public GetActorEventsUseCase getEventsByActorUseCase(EventRepository eventRepository, ActorRepository actorRepository) {
        return new GetActorEventsUseCase(eventRepository, actorRepository);
    }

    @Bean
    public GetActorsUseCase getAllActorsUseCase(ActorRepository repository) {
        return new GetActorsUseCase(repository);
    }

    @Bean
    public GetActorsStreakUseCase getStreakSortedActorsUseCase(ActorRepository repository) {
        return new GetActorsStreakUseCase(repository);
    }

    @Bean
    public UpdateActorUseCase updateActorUseCase(ActorRepository repository) {
        return new UpdateActorUseCase(repository);
    }
}
