package com.hackerrank.github.persistence.jpa.repositories;

import com.hackerrank.github.core.entities.Actor;
import com.hackerrank.github.core.entities.Event;
import com.hackerrank.github.core.entities.EventRepository;
import com.hackerrank.github.core.entities.Identity;
import com.hackerrank.github.persistence.jpa.entities.EventData;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
@Transactional
public class EventRepositoryAdapter implements EventRepository {
    private JpaEventRepository jpaEventRepository;

    public EventRepositoryAdapter(JpaEventRepository jpaEventRepository) {
        this.jpaEventRepository = jpaEventRepository;
    }

    @Override
    public boolean exists(Event event) {
        return jpaEventRepository.existsById(event.getId().getNumber());
    }

    @Override
    public Event persist(Event event) {
        EventData eventData = EventData.from(event);

        return jpaEventRepository.save(eventData).fromThis();
    }

    @Override
    public void deleteAll() {
        jpaEventRepository.deleteAll();
    }

    @Override
    public List<Event> getAll() {
        return jpaEventRepository
                .findAllByOrderByIdAsc()
                .stream()
                .map(EventData::fromThis)
                .collect(toList());
    }

    @Override
    public List<Event> getAllByActorIdOrderById(Identity id) {
        return jpaEventRepository
                .findByActorId(id.getNumber(), new Sort(Sort.Direction.DESC, "id"))
                .stream()
                .map(EventData::fromThis)
                .collect(toList());
    }

    @Override
    public Actor getAllByActorOrderByCreatedAt(Actor actor) {
        List<Event> events = jpaEventRepository
                .findByActorId(actor.getId().getNumber(), new Sort(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(EventData::fromThis)
                .collect(toList());

        actor.setEvents(events);

        return actor;
    }
}
