package com.hackerrank.github.presenter.rest.api.actor;

import com.hackerrank.github.core.usecases.actor.GetActorsStreakUseCase;
import com.hackerrank.github.presenter.rest.api.shared.ActorResponse;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class GetStreakSortedActorsAdapter {
    public static List<ActorResponse> output(GetActorsStreakUseCase.OutputValues outputValues) {

        return outputValues
                .getActors()
                .stream()
                .map(ActorResponse::from)
                .collect(toList());
    }
}
