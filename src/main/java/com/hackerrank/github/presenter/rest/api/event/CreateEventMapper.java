package com.hackerrank.github.presenter.rest.api.event;

import com.hackerrank.github.core.domain.Actor;
import com.hackerrank.github.core.domain.Event;
import com.hackerrank.github.core.domain.EventType;
import com.hackerrank.github.core.domain.Identity;
import com.hackerrank.github.core.domain.Repo;
import com.hackerrank.github.core.usecases.event.CreateEventUseCase;
import com.hackerrank.github.presenter.rest.api.common.ActorRequest;
import com.hackerrank.github.presenter.rest.api.common.RepoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

public class CreateEventMapper {

    public static ResponseEntity<ResponseEntity<Object>> output(CreateEventUseCase.OutputValues value, HttpServletRequest httpServletRequest) {
        URI location = ServletUriComponentsBuilder
                .fromContextPath(httpServletRequest)
                .path("/events/{id}")
                .buildAndExpand(value.getId().getNumber())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    public static CreateEventUseCase.InputVales input(EventRequest request) {
        return new CreateEventUseCase.InputVales(
                Event.builder()
                        .id(toIdentity(request.getId()))
                        .type(EventType.from(request.getType()))
                        .actor(toActor(request.getActor()))
                        .repo(toRepo(request.getRepo()))
                        .createdAt(request.getCreatedAt())
                        .build()
        );
    }

    private static Actor toActor(ActorRequest request) {
        return Actor.builder()
                .id(toIdentity(request.getId()))
                .login(request.getLogin())
                .avatar(request.getAvatarUrl())
                .build();
    }

    private static Repo toRepo(RepoRequest request) {
        return new Repo(toIdentity(request.getId()), request.getName(), toURI(request.getUrl()));
    }

    private static URI toURI(String url) {
        return URI.create(url);
    }

    private static Identity toIdentity(Long id) {
        return new Identity(id);
    }
}
