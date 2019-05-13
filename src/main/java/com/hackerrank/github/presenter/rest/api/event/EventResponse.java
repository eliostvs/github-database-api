package com.hackerrank.github.presenter.rest.api.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackerrank.github.core.entities.Event;
import com.hackerrank.github.presenter.rest.api.shared.ActorResponse;
import com.hackerrank.github.presenter.rest.api.shared.RepoResponse;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class EventResponse {
    private final Long id;
    private final String type;
    private final ActorResponse actor;
    private final RepoResponse repo;

    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public static EventResponse from(Event event) {
        return new EventResponse(
                event.getId().getNumber(),
                event.getType().toString(),
                ActorResponse.from(event.getActor()),
                RepoResponse.from(event.getRepo()),
                event.getCreatedAt()
        );
    }
}
