package com.hackerrank.github.persistence.jpa.repositories;

import com.hackerrank.github.core.entities.Actor;
import com.hackerrank.github.core.entities.ActorRepository;
import com.hackerrank.github.core.entities.Identity;
import com.hackerrank.github.persistence.jpa.entities.ActorData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Repository
public class ActorRepositoryImpl implements ActorRepository {
    private JpaActorRepository jpaRepository;

    public ActorRepositoryImpl(JpaActorRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public boolean exists(Identity id) {
        return jpaRepository.existsById(id.getNumber());
    }

    @Override
    public Actor update(Actor newActor) {
        return jpaRepository.save(ActorData.from(newActor)).fromThis();
    }

    @Override
    public Optional<Actor> findById(Identity id) {
        return jpaRepository
                .findById(id.getNumber())
                .map(ActorData::fromThis);
    }

    @Override
    public List<Actor> getAll() {
        return jpaRepository
                .findAllOrderByLargestNumberEvents()
                .stream()
                .map(ActorData::fromThis)
                .collect(toList());
    }

    @Override
    public List<Actor> getStreak() {
        return jpaRepository
                .findAllOrderByStreak()
                .stream()
                .map(ActorData::fromThis)
                .collect(toList());
    }

    @Override
    public void persist(Actor actor) {
        jpaRepository.save(ActorData.from(actor));
    }
}
