package com.hackerrank.github.presenter.rest.api.event;

import com.hackerrank.github.core.usecases.UseCase;
import com.hackerrank.github.core.usecases.UseCaseExecutor;
import com.hackerrank.github.core.usecases.event.CreateEventUseCase;
import com.hackerrank.github.core.usecases.event.DeleteEventsUseCase;
import com.hackerrank.github.core.usecases.event.GetEventsUseCase;
import com.hackerrank.github.core.usecases.event.GetActorEventsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Component
public class EventController implements EventResource {
    private UseCaseExecutor useCaseExecutor;
    private CreateEventUseCase createEventUseCase;
    private DeleteEventsUseCase deleteEventsUseCase;
    private GetEventsUseCase getEventsUseCase;
    private GetActorEventsUseCase getActorEventsUseCase;

    public EventController(UseCaseExecutor useCaseExecutor,
                           CreateEventUseCase createEventUseCase,
                           DeleteEventsUseCase deleteEventsUseCase,
                           GetEventsUseCase getEventsUseCase,
                           GetActorEventsUseCase getActorEventsUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.createEventUseCase = createEventUseCase;
        this.deleteEventsUseCase = deleteEventsUseCase;
        this.getEventsUseCase = getEventsUseCase;
        this.getActorEventsUseCase = getActorEventsUseCase;
    }

    @Override
    public CompletableFuture<ResponseEntity<ResponseEntity<Object>>> create(@Valid @RequestBody EventRequest request,
                                                                            HttpServletRequest httpServletRequest) {
        return useCaseExecutor.execute(
                createEventUseCase,
                CreateEventAdapter.input(request),
                (outputValues) -> CreateEventAdapter.output(outputValues, httpServletRequest)
        );
    }

    @Override
    public CompletableFuture<ResponseEntity<Object>> deleteAll() {
        return useCaseExecutor.execute(
                deleteEventsUseCase,
                UseCase.noInput(),
                DeleteAllEventsAdapter::output
        );
    }

    @Override
    public CompletableFuture<List<EventResponse>> getAll() {
        return useCaseExecutor.execute(
                getEventsUseCase,
                UseCase.noInput(),
                GetAllEventsAdapter::output
        );
    }

    @Override
    public CompletableFuture<List<EventResponse>> getByActorId(@PathVariable("id") long id) {
        return useCaseExecutor.execute(
                getActorEventsUseCase,
                GetEventsByActorAdapter.input(id),
                GetEventsByActorAdapter::output
        );
    }
}
