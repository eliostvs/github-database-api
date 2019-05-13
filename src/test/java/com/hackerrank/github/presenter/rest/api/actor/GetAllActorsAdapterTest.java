package com.hackerrank.github.presenter.rest.api.actor;

import com.hackerrank.github.core.entities.Actor;
import com.hackerrank.github.core.entities.EntitiesMother;
import com.hackerrank.github.core.usecases.actor.GetActorsUseCase;
import com.hackerrank.github.presenter.rest.api.shared.ActorResponse;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class GetAllActorsAdapterTest {
    @Test
    public void output() throws Exception {
        // given
        List<Actor> actors = EntitiesMother.randomActors();
        GetActorsUseCase.OutputValues input = new GetActorsUseCase.OutputValues(actors);

        // when
        List<ActorResponse> actual = GetAllActorsAdapter.output(input);

        // then
        List<ActorResponse> expected = actors.stream().map(ActorResponse::from).collect(toList());
        assertThat(actual).isEqualTo(expected);
    }
}