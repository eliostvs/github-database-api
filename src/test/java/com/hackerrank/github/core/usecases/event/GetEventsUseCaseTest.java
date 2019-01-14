package com.hackerrank.github.core.usecases.event;

import com.hackerrank.github.core.domain.EntitiesMother;
import com.hackerrank.github.core.domain.Event;
import com.hackerrank.github.core.domain.EventRepository;
import com.hackerrank.github.core.usecases.UseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GetEventsUseCaseTest {

    @InjectMocks
    private GetEventsUseCase useCase;

    @Mock
    private EventRepository repository;

    @Test
    public void execute() throws Exception {
        // Given
        List<Event> events = EntitiesMother.randomEvents();
        GetEventsUseCase.OutputValues expected = new GetEventsUseCase.OutputValues(events);
        doReturn(events)
                .when(repository)
                .getAll();

        // When
        GetEventsUseCase.OutputValues actual = useCase.execute(UseCase.noInput());

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}