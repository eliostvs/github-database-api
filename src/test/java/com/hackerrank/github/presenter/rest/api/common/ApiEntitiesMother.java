package com.hackerrank.github.presenter.rest.api.common;

import com.hackerrank.github.core.domain.EntitiesMother;
import com.hackerrank.github.presenter.rest.api.event.EventRequest;

public class ApiEntitiesMother extends EntitiesMother {
    public static EventRequest randomCreateEventRequest() {
        return new EventRequest(
                randomNumber(),
                "PushEvent",
                randomRepoRequest(),
                randomActorRequest(),
                randomDate()
        );
    }

    public static ActorRequest randomActorRequest() {
        return new ActorRequest(randomNumber(), randomName(), randomAvatar());
    }

    protected static RepoRequest randomRepoRequest() {
        return new RepoRequest(randomNumber(), randomName(), randomUrl());
    }

}
