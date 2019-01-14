package com.hackerrank.github.core.domain;

import java.util.List;

public interface EventRepository {
    boolean exists(Event event);

    Event persist(Event event);

    void deleteAll();

    List<Event> getAll();

    List<Event> getAllByActorIdOrderById(Identity id);

    Actor getAllByActorOrderByCreatedAt(Actor actor);
}
