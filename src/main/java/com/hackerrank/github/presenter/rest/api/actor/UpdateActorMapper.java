package com.hackerrank.github.presenter.rest.api.actor;

import com.hackerrank.github.core.domain.Actor;
import com.hackerrank.github.core.domain.Identity;
import com.hackerrank.github.core.usecases.actor.UpdateActorUseCase;
import com.hackerrank.github.presenter.rest.api.common.ActorRequest;
import org.springframework.http.ResponseEntity;

public class UpdateActorMapper {
    public static ResponseEntity<Object> output(UpdateActorUseCase.OutputValues outputValues) {
        return ResponseEntity.ok().build();
    }

    public static UpdateActorUseCase.InputValues input(ActorRequest request) {
        return new UpdateActorUseCase.InputValues(toActor(request));
    }

    private static Actor toActor(ActorRequest request) {
        return Actor.builder()
                .id(toIdentity(request.getId()))
                .login(request.getLogin())
                .avatar(request.getAvatarUrl())
                .build();
    }

    private static Identity toIdentity(Long id) {
        return new Identity(id);
    }
}
