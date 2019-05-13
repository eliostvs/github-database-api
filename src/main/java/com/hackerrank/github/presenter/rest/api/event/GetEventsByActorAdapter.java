package com.hackerrank.github.presenter.rest.api.event;

import com.hackerrank.github.core.entities.Identity;
import com.hackerrank.github.core.usecases.event.GetActorEventsUseCase;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class GetEventsByActorAdapter {
    public static GetActorEventsUseCase.InputValues input(long id) {
        return new GetActorEventsUseCase.InputValues(new Identity(id));
    }

    public static List<EventResponse> output(GetActorEventsUseCase.OutputValues outputValues) {
        return outputValues
                .getEvents()
                .stream()
                .map(EventResponse::from)
                .collect(toList());
    }
}
