package com.hackerrank.github.core.usecases.event;

import com.hackerrank.github.core.domain.EventRepository;
import com.hackerrank.github.core.usecases.UseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeleteEventsUseCaseTest {

    @InjectMocks
    private DeleteEventsUseCase useCase;

    @Mock
    private EventRepository repository;

    @Test
    public void executeReturnsTrue() {
        // Given
        DeleteEventsUseCase.OutputValues expected = new DeleteEventsUseCase.OutputValues(true);

        // When
        DeleteEventsUseCase.OutputValues actual = useCase.execute(UseCase.noInput());

        // Then
        assertThat(actual).isEqualTo(expected);
        verify(repository).deleteAll();
    }
}