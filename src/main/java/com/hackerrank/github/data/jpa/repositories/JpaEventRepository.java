package com.hackerrank.github.data.jpa.repositories;

import com.hackerrank.github.data.jpa.entities.EventData;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEventRepository extends JpaRepository<EventData, Long> {
    List<EventData> findAllByOrderByIdAsc();

    List<EventData> findByActorIdOrderByIdAsc(Long id);

    List<EventData> findByActorId(Long id, Sort sort);
}
