package com.hackerrank.github.data.jpa.repositories;

import com.hackerrank.github.core.domain.Actor;
import com.hackerrank.github.core.domain.Event;
import com.hackerrank.github.core.domain.EventRepository;
import com.hackerrank.github.core.domain.Identity;
import com.hackerrank.github.data.jpa.entities.EventData;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
@Transactional
public class EventRepositoryImpl implements EventRepository {

    private JpaEventRepository jpaEventRepository;

    public EventRepositoryImpl(JpaEventRepository jpaEventRepository) {
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
