package com.hackerrank.github.core.usecases.event;

import com.hackerrank.github.core.entities.ActorRepository;
import com.hackerrank.github.core.entities.EntityNotFoundException;
import com.hackerrank.github.core.entities.Event;
import com.hackerrank.github.core.entities.EventRepository;
import com.hackerrank.github.core.entities.Identity;
import com.hackerrank.github.core.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class GetActorEventsUseCase extends UseCase<GetActorEventsUseCase.InputValues, GetActorEventsUseCase.OutputValues> {

    private EventRepository eventRepository;
    private ActorRepository actorRepository;

    public GetActorEventsUseCase(EventRepository eventRepository, ActorRepository actorRepository) {
        this.eventRepository = eventRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Identity id = input.getId();

        if (!actorRepository.exists(id)) {
            throw new EntityNotFoundException("Actor", id);
        }

        return new OutputValues(eventRepository.getAllByActorIdOrderById(id));
    }

    @Value
    public static final class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    public static final class OutputValues implements UseCase.OutputValues {
        private final List<Event> events;
    }
}
