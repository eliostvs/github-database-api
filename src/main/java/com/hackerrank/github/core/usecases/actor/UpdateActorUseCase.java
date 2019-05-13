package com.hackerrank.github.core.usecases.actor;

import com.hackerrank.github.core.entities.Actor;
import com.hackerrank.github.core.entities.ActorRepository;
import com.hackerrank.github.core.entities.BusinessException;
import com.hackerrank.github.core.entities.EntityNotFoundException;
import com.hackerrank.github.core.usecases.UseCase;
import lombok.Value;
import org.springframework.transaction.annotation.Transactional;

public class UpdateActorUseCase extends UseCase<UpdateActorUseCase.InputValues, UpdateActorUseCase.OutputValues> {

    private ActorRepository repository;

    public UpdateActorUseCase(ActorRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public OutputValues execute(InputValues input) {
        Actor updated = input.getActor();

        return repository.findById(updated.getId())
                .map((current) -> {
                    validateUpdate(current, updated);
                    return new OutputValues(repository.update(updated));
                })
                .orElseThrow(() -> new EntityNotFoundException("Actor", updated.getId()));
    }

    private void validateUpdate(Actor current, Actor updated) {
        Actor.UpdateValidation updateValidation = current.validateUpdate(updated);

        if (!updateValidation.isSuccess()) {
            throw new BusinessException(updateValidation.getReason());
        }
    }

    @Value
    public static final class InputValues implements UseCase.InputValues {
        private final Actor actor;
    }

    @Value
    public static final class OutputValues implements UseCase.OutputValues {
        private final Actor actor;
    }
}
