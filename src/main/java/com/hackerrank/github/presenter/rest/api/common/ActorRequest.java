package com.hackerrank.github.presenter.rest.api.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Value
public class ActorRequest {
    @Min(1)
    private final Long id;

    @NonNull
    @NotBlank
    private final String login;

    @NonNull
    @NotBlank
    @JsonProperty("avatar_url")
    private final String avatarUrl;
}
