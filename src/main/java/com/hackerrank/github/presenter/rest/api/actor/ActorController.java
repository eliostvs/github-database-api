package com.hackerrank.github.presenter.rest.api.actor;

import com.hackerrank.github.core.usecases.UseCase;
import com.hackerrank.github.core.usecases.UseCaseExecutor;
import com.hackerrank.github.core.usecases.actor.GetActorsUseCase;
import com.hackerrank.github.core.usecases.actor.GetActorsStreakUseCase;
import com.hackerrank.github.core.usecases.actor.UpdateActorUseCase;
import com.hackerrank.github.presenter.rest.api.common.ActorRequest;
import com.hackerrank.github.presenter.rest.api.common.ActorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ActorController implements ActorResource {
    private UseCaseExecutor useCaseExecutor;
    private UpdateActorUseCase updateActorUseCase;
    private GetActorsUseCase getActorsUseCase;
    private GetActorsStreakUseCase getActorsStreakUseCase;

    public ActorController(UseCaseExecutor useCaseExecutor,
                           UpdateActorUseCase updateActorUseCase,
                           GetActorsUseCase getActorsUseCase,
                           GetActorsStreakUseCase getActorsStreakUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.updateActorUseCase = updateActorUseCase;
        this.getActorsUseCase = getActorsUseCase;
        this.getActorsStreakUseCase = getActorsStreakUseCase;
    }

    @Override
    public CompletableFuture<ResponseEntity<Object>> create(@Valid @RequestBody ActorRequest request) {
        return useCaseExecutor.execute(
                updateActorUseCase,
                UpdateActorMapper.input(request),
                UpdateActorMapper::output
        );
    }

    @Override
    public CompletableFuture<List<ActorResponse>> getAll() {
        return useCaseExecutor.execute(
                getActorsUseCase,
                UseCase.noInput(),
                GetAllActorsMapper::output
        );
    }

    @Override
    public CompletableFuture<List<ActorResponse>> getStreak() {
        return useCaseExecutor.execute(
                getActorsStreakUseCase,
                UseCase.noInput(),
                GetStreakSortedActorsMapper::output
        );
    }
}
