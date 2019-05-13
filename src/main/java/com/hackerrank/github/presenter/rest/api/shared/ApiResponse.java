package com.hackerrank.github.presenter.rest.api.shared;

import lombok.Value;

@Value
public class ApiResponse {
    private final Boolean success;
    private final String message;
}
