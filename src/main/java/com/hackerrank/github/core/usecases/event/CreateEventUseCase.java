package com.hackerrank.github.core.usecases.event;

import com.hackerrank.github.core.domain.Actor;
import com.hackerrank.github.core.domain.ActorRepository;
import com.hackerrank.github.core.domain.BusinessException;
import com.hackerrank.github.core.domain.Event;
import com.hackerrank.github.core.domain.EventRepository;
import com.hackerrank.github.core.domain.Identity;
import com.hackerrank.github.core.usecases.UseCase;
import lombok.Value;

public class CreateEventUseCase extends UseCase<CreateEventUseCase.InputVales, CreateEventUseCase.OutputValues> {
    private EventRepository eventRepository;
    private ActorRepository actorRepository;

    public CreateEventUseCase(EventRepository eventRepository, ActorRepository actorRepository) {
        this.eventRepository = eventRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public OutputValues execute(InputVales input) {
        final Event newEvent = input.getEvent();

        validateInsert(newEvent);

        Event savedEvent = eventRepository.persist(newEvent);

        Actor actor = eventRepository.getAllByActorOrderByCreatedAt(newEvent.getActor());
        actor.calculateStreak();

        actorRepository.persist(actor);

        return new OutputValues(savedEvent.getId());
    }

    private void validateInsert(Event event) {
        if (eventRepository.exists(event)) {
            Long number = event.getId().getNumber();
            String errorMessage = String.format("Registration failed because th event with ID '%d' already exists", number);
            throw new BusinessException(errorMessage);
        }
    }

    @Value
    public static final class InputVales implements UseCase.InputValues {
        private final Event event;
    }

    @Value
    public static final class OutputValues implements UseCase.OutputValues {
        private final Identity id;
    }
}
