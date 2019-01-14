package com.hackerrank.github.core.usecases.event;

import com.hackerrank.github.core.domain.Actor;
import com.hackerrank.github.core.domain.ActorRepository;
import com.hackerrank.github.core.domain.BusinessException;
import com.hackerrank.github.core.domain.EntitiesMother;
import com.hackerrank.github.core.domain.Event;
import com.hackerrank.github.core.domain.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateEventUseCaseTest {

    @InjectMocks
    private CreateEventUseCase useCase;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ActorRepository actorRepository;

    @Test
    public void createEventReturnsTrueWhenEventDoesNotExist() {
        // Given
        Event event = EntitiesMother.randomEvent();
        doReturn(false).when(eventRepository).exists(eq(event));
        doReturn(event).when(eventRepository).persist(eq(event));

        Actor mockActor = mock(Actor.class);
        doReturn(mockActor).when(eventRepository).getAllByActorOrderByCreatedAt(eq(event.getActor()));

        // When
        CreateEventUseCase.OutputValues actual = useCase.execute(new CreateEventUseCase.InputVales(event));

        // Then
        assertThat(actual.getId()).isEqualTo(event.getId());
        verify(actorRepository).persist(eq(mockActor));
        verify(mockActor).calculateStreak();
    }

    @Test
    public void createEventShouldThrowExceptionWenEventAlreadyExist() {
        // Given
        Event event = EntitiesMother.randomEvent();
        CreateEventUseCase.InputVales input = new CreateEventUseCase.InputVales(event);
        doReturn(true).when(eventRepository).exists(eq(event));

        // Then
        String errorMessage = String.format("Registration failed because th event with ID '%d' already exists", event.getId().getNumber());
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(BusinessException.class)
                .hasMessage(errorMessage);

        // And
        verify(eventRepository, never()).persist(any());
    }
}