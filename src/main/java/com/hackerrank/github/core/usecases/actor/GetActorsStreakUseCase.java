package com.hackerrank.github.core.usecases.actor;

import com.hackerrank.github.core.domain.Actor;
import com.hackerrank.github.core.domain.ActorRepository;
import com.hackerrank.github.core.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class GetActorsStreakUseCase extends UseCase<UseCase.NoInput, GetActorsStreakUseCase.OutputValues> {

    private ActorRepository repository;

    public GetActorsStreakUseCase(ActorRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(NoInput input) {
        return new OutputValues(repository.getStreak());
    }

    @Value
    public static final class OutputValues implements UseCase.OutputValues {
        private final List<Actor> actors;
    }
}
