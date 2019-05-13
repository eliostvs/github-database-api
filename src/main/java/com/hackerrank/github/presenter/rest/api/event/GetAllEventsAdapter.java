package com.hackerrank.github.presenter.rest.api.event;

import com.hackerrank.github.core.usecases.event.GetEventsUseCase;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class GetAllEventsAdapter {

    public static List<EventResponse> output(GetEventsUseCase.OutputValues outputValues) {
        return outputValues
                .getEvents()
                .stream()
                .map(EventResponse::from)
                .collect(toList());
    }
}
