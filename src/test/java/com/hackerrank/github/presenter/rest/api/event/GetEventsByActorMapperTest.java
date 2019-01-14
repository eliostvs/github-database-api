package com.hackerrank.github.presenter.rest.api.event;

import com.hackerrank.github.core.domain.EntitiesMother;
import com.hackerrank.github.core.domain.Event;
import com.hackerrank.github.core.domain.Identity;
import com.hackerrank.github.core.usecases.event.GetActorEventsUseCase;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class GetEventsByActorMapperTest {

    @Test
    public void input() throws Exception {
        long id = EntitiesMother.randomNumber();

        // When
        GetActorEventsUseCase.InputValues actual = GetEventsByActorMapper.input(id);

        // Then
        assertThat(actual.getId()).isEqualTo(new Identity(id));
    }

    @Test
    public void output() throws Exception {
        // Given
        List<Event> events = EntitiesMother.randomEvents();
        List<EventResponse> expected = events.stream().map(EventResponse::from).collect(toList());

        // When
        List<EventResponse> actual = GetEventsByActorMapper.output(new GetActorEventsUseCase.OutputValues(events));

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}