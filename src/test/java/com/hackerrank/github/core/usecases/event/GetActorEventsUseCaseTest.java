package com.hackerrank.github.core.usecases.event;

import com.hackerrank.github.core.domain.ActorRepository;
import com.hackerrank.github.core.domain.EntitiesMother;
import com.hackerrank.github.core.domain.EntityNotFoundException;
import com.hackerrank.github.core.domain.Event;
import com.hackerrank.github.core.domain.EventRepository;
import com.hackerrank.github.core.domain.Identity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class GetActorEventsUseCaseTest {

    @InjectMocks
    private GetActorEventsUseCase useCase;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ActorRepository actorRepository;

    @Test
    public void executeReturnsActorEventsWhenActorsIsFound() throws Exception {
        // Given
        Identity identity = EntitiesMother.randomIdentity();
        GetActorEventsUseCase.InputValues input = new GetActorEventsUseCase.InputValues(identity);
        Event expected = EntitiesMother.randomEvent();

        doReturn(true)
                .when(actorRepository)
                .exists(eq(identity));

        doReturn(singletonList(expected))
                .when(eventRepository)
                .getAllByActorIdOrderById(eq(identity));

        // When
        GetActorEventsUseCase.OutputValues actual = useCase.execute(input);

        // Then
        assertThat(actual.getEvents()).isEqualTo(singletonList(expected));
    }

    @Test
    public void executeThrowsExceptionWhenActorIsNotFound() throws Exception {
        // Given
        Identity identity = EntitiesMother.randomIdentity();
        GetActorEventsUseCase.InputValues input = new GetActorEventsUseCase.InputValues(identity);
        doThrow(new EntityNotFoundException("Actor", identity))
                .when(actorRepository)
                .exists(eq(identity));

        // Then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(EntityNotFoundException.class);
    }
}