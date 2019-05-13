package com.hackerrank.github.presenter.rest.api.event;

import com.hackerrank.github.core.entities.EntitiesMother;
import com.hackerrank.github.core.entities.Event;
import com.hackerrank.github.core.entities.Identity;
import com.hackerrank.github.core.usecases.event.GetActorEventsUseCase;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class GetEventsByActorAdapterTest {

    @Test
    public void input() throws Exception {
        long id = EntitiesMother.randomNumber();

        // When
        GetActorEventsUseCase.InputValues actual = GetEventsByActorAdapter.input(id);

        // Then
        assertThat(actual.getId()).isEqualTo(new Identity(id));
    }

    @Test
    public void output() throws Exception {
        // Given
        List<Event> events = EntitiesMother.randomEvents();
        List<EventResponse> expected = events.stream().map(EventResponse::from).collect(toList());

        // When
        List<EventResponse> actual = GetEventsByActorAdapter.output(new GetActorEventsUseCase.OutputValues(events));

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}