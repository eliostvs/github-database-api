package com.hackerrank.github.presenter.rest.api.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackerrank.github.core.entities.Actor;
import lombok.Value;

@Value
public class ActorResponse {
    private final Long id;

    private final String login;

    @JsonProperty("avatar_url")
    private final String avatarUrl;

    public static ActorResponse from(Actor actor) {
        return new ActorResponse(
                actor.getId().getNumber(),
                actor.getLogin(),
                actor.getAvatar()
        );
    }
}
