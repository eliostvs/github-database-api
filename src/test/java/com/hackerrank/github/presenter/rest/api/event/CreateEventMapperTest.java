package com.hackerrank.github.presenter.rest.api.event;

import com.hackerrank.github.core.domain.EntitiesMother;
import com.hackerrank.github.core.domain.Event;
import com.hackerrank.github.core.domain.EventType;
import com.hackerrank.github.core.domain.Identity;
import com.hackerrank.github.core.usecases.event.CreateEventUseCase;
import com.hackerrank.github.presenter.rest.api.common.ApiEntitiesMother;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateEventMapperTest {

    @Test
    public void input() {
        // Given
        EventRequest request = ApiEntitiesMother.randomCreateEventRequest();

        // When
        Event event = CreateEventMapper.input(request).getEvent();

        // Then
        assertThat(event.getId().getNumber()).isEqualTo(request.getId());
        assertThat(event.getCreatedAt()).isEqualTo(request.getCreatedAt());
        assertThat(event.getType()).isEqualTo(EventType.PUSH_EVENT);
        assertThat(event.getRepo().getId()).isEqualTo(new Identity(request.getRepo().getId()));
        assertThat(event.getRepo().getName()).isEqualTo(request.getRepo().getName());
        assertThat(event.getRepo().getUrl()).isEqualTo(URI.create(request.getRepo().getUrl()));
        assertThat(event.getActor().getId()).isEqualTo(new Identity(request.getActor().getId()));
        assertThat(event.getActor().getAvatar()).isEqualTo(request.getActor().getAvatarUrl());
        assertThat(event.getActor().getLogin()).isEqualTo(request.getActor().getLogin());
    }

    @Test
    public void output() {
        // Given
        Identity identity = EntitiesMother.randomIdentity();
        CreateEventUseCase.OutputValues output = new CreateEventUseCase.OutputValues(identity);
        HttpServletRequest httpServletRequest = new MockHttpServletRequest("", "");

        // When
        ResponseEntity<ResponseEntity<Object>> actual = CreateEventMapper.output(output, httpServletRequest);

        // Then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getHeaders().getLocation().toString()).isEqualTo("http://localhost/events/" + identity.getNumber());
    }
}