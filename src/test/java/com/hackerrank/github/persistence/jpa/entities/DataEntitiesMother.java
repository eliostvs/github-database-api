package com.hackerrank.github.persistence.jpa.entities;

import com.hackerrank.github.core.entities.EntitiesMother;
import com.hackerrank.github.core.entities.EventType;

import java.util.List;
import java.util.function.Function;

public final class DataEntitiesMother extends EntitiesMother {

    public static List<EventData> randomEventsData() {
        return randomItemsOf(DataEntitiesMother::randomEventData);
    }

    public static EventData randomEventData() {
        return EventData.builder()
                .id(randomNumber())
                .createdAt(randomDate())
                .type(EventType.PUSH_EVENT)
                .build();
    }

    public static RepoData randomRepoData() {
        return RepoData.builder()
                .id(randomNumber())
                .name(randomName())
                .url(randomUrl())
                .build();
    }

    public static EventData randomEventData(Function<EventData, EventData> postConstruct) {
        EventData event = EventData.builder()
                .id(randomNumber())
                .createdAt(randomDate())
                .type(EventType.PUSH_EVENT)
                .build();

        return postConstruct.apply(event);
    }

    public static ActorData randomActorData() {
        return ActorData
                .builder()
                .id(randomNumber())
                .avatar(randomAvatar())
                .login(randomName())
                .build();
    }

    public static ActorData randomActorData(Function<ActorData, ActorData> postConstruct) {
        ActorData actor = ActorData
                .builder()
                .id(randomNumber())
                .avatar(randomAvatar())
                .login(randomName())
                .build();

        return postConstruct.apply(actor);
    }
}
