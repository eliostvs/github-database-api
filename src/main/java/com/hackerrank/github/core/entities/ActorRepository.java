package com.hackerrank.github.core.entities;

import java.util.List;
import java.util.Optional;

public interface ActorRepository {
    boolean exists(Identity id);

    Actor update(Actor newActor);

    Optional<Actor> findById(Identity id);

    List<Actor> getAll();

    List<Actor> getStreak();

    void persist(Actor actor);
}
