package com.hackerrank.github.core.usecases.event;

import com.hackerrank.github.core.domain.EventRepository;
import com.hackerrank.github.core.usecases.UseCase;
import lombok.Value;
import org.springframework.transaction.annotation.Transactional;

public class DeleteEventsUseCase extends UseCase<UseCase.NoInput, DeleteEventsUseCase.OutputValues> {

    private EventRepository repository;

    public DeleteEventsUseCase(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public OutputValues execute(NoInput input) {
        repository.deleteAll();

        return new OutputValues(true);
    }

    @Value
    public static final class OutputValues implements UseCase.OutputValues {
        private final boolean success;
    }
}
