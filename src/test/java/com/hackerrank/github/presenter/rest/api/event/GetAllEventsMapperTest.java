package com.hackerrank.github.presenter.rest.api.event;

import com.hackerrank.github.core.domain.EntitiesMother;
import com.hackerrank.github.core.domain.Event;
import com.hackerrank.github.core.usecases.event.GetEventsUseCase;
import com.hackerrank.github.presenter.rest.api.common.ActorResponse;
import com.hackerrank.github.presenter.rest.api.common.RepoResponse;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class GetAllEventsMapperTest {

    @Test
    public void output() throws Exception {
        // Given
        Event event = EntitiesMother.randomEvent();
        GetEventsUseCase.OutputValues input = new GetEventsUseCase.OutputValues(singletonList(event));

        ActorResponse actorResponse = new ActorResponse(
                event.getActor().getId().getNumber(),
                event.getActor().getLogin(),
                event.getActor().getAvatar()
        );

        RepoResponse repoResponse = new RepoResponse(
                event.getRepo().getId().getNumber(),
                event.getRepo().getName(),
                event.getRepo().getUrl().toString()
        );

        EventResponse expected = new EventResponse(
                event.getId().getNumber(),
                "PushEvent",
                actorResponse,
                repoResponse,
                event.getCreatedAt()
        );

        // When
        List<EventResponse> actual = GetAllEventsMapper.output(input);

        // Then
        assertThat(actual).isEqualTo(singletonList(expected));
    }
}