package com.hackerrank.github.persistence.jpa.repositories;

import com.hackerrank.github.persistence.jpa.entities.ActorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaActorRepository extends JpaRepository<ActorData, Long> {
    @Query("select e.actor from Event e group by e.actor.id order by count(e.actor.id) desc, max(e.createdAt) desc, e.actor.login")
    List<ActorData> findAllOrderByLargestNumberEvents();

    @Query("SELECT act FROM Actor act ORDER BY act.streak DESC, act.latestEvent DESC, act.login ASC")
    List<ActorData> findAllOrderByStreak();
}
