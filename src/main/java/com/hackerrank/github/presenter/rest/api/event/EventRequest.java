package com.hackerrank.github.presenter.rest.api.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackerrank.github.presenter.rest.api.common.ActorRequest;
import com.hackerrank.github.presenter.rest.api.common.RepoRequest;
import lombok.NonNull;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
public class EventRequest {
    @NonNull
    @Min(1)
    private final Long id;

    @NotNull
    private final String type;

    @NonNull
    @Valid
    private final RepoRequest repo;

    @NonNull
    @Valid
    private final ActorRequest actor;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private final LocalDateTime createdAt;
}
