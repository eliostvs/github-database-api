package com.hackerrank.github.presenter.rest.api.actor;

import com.hackerrank.github.core.entities.Actor;
import com.hackerrank.github.core.entities.EntitiesMother;
import com.hackerrank.github.core.usecases.actor.GetActorsStreakUseCase;
import com.hackerrank.github.presenter.rest.api.shared.ActorResponse;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class GetStreakSortedActorsAdapterTest {
    @Test
    public void output() {
        // given
        List<Actor> actors = EntitiesMother.randomActors();
        GetActorsStreakUseCase.OutputValues input = new GetActorsStreakUseCase.OutputValues(actors);

        // when
        List<ActorResponse> actual = GetStreakSortedActorsAdapter.output(input);

        // then
        List<ActorResponse> expected = actors.stream().map(ActorResponse::from).collect(toList());
        assertThat(actual).isEqualTo(expected);
    }
}