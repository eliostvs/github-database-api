package com.hackerrank.github.presenter.rest.api.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackerrank.github.core.domain.Actor;
import lombok.Value;

@Value
public class ActorResponse {
    private Long id;
    private String login;
    @JsonProperty("avatar_url")
    private String avatar;

    public static ActorResponse from(Actor actor) {
        return new ActorResponse(
                actor.getId().getNumber(),
                actor.getLogin(),
                actor.getAvatar()
        );
    }
}
