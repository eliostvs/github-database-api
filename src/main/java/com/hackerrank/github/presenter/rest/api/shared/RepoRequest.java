package com.hackerrank.github.presenter.rest.api.shared;

import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Value
public class RepoRequest {
    @Min(1)
    private final Long id;

    @NonNull
    @NotBlank
    private final String name;

    @NonNull
    @NotBlank
    private final String url;
}
