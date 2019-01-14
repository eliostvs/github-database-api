package com.hackerrank.github.core.usecases.event;

import com.hackerrank.github.core.domain.Event;
import com.hackerrank.github.core.domain.EventRepository;
import com.hackerrank.github.core.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class GetEventsUseCase extends UseCase<UseCase.NoInput, GetEventsUseCase.OutputValues> {

    private EventRepository repository;

    public GetEventsUseCase(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(NoInput ignored) {
        return new OutputValues(repository.getAll());
    }

    @Value
    public static final class OutputValues implements UseCase.OutputValues {
        private final List<Event> events;
    }
}
