package com.hackerrank.github.core.usecases.actor;

import com.hackerrank.github.core.entities.Actor;
import com.hackerrank.github.core.entities.ActorRepository;
import com.hackerrank.github.core.entities.EntitiesMother;
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
public class GetActorsStreakUseCaseTest {

    @InjectMocks
    private GetActorsStreakUseCase useCase;

    @Mock
    private ActorRepository repository;

    @Test
    public void execute() {
        // given
        List<Actor> expected = EntitiesMother.randomActors();

        doReturn(expected)
                .when(repository)
                .getStreak();

        // when
        GetActorsStreakUseCase.OutputValues actual = useCase.execute(UseCase.noInput());

        // then
        assertThat(actual.getActors()).isEqualTo(expected);
    }
}