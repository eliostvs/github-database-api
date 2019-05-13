package com.hackerrank.github.core.usecases.actor;

import com.hackerrank.github.core.entities.Actor;
import com.hackerrank.github.core.entities.ActorRepository;
import com.hackerrank.github.core.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class GetActorsUseCase extends UseCase<UseCase.NoInput, GetActorsUseCase.OutputValues> {
    private ActorRepository repository;

    public GetActorsUseCase(ActorRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(NoInput ignored) {
        return new OutputValues(repository.getAll());
    }

    @Value
    public static final class OutputValues implements UseCase.OutputValues {
        private final List<Actor> actors;
    }
}
