package com.hackerrank.github.persistence.jpa.repositories;

import com.hackerrank.github.core.entities.Actor;
import com.hackerrank.github.core.entities.EntitiesMother;
import com.hackerrank.github.core.entities.Event;
import com.hackerrank.github.core.entities.Identity;
import com.hackerrank.github.persistence.jpa.entities.EventData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EventRepositoryAdapterTest {

    @InjectMocks
    private EventRepositoryAdapter repository;

    @Mock
    private JpaEventRepository jpaEventRepository;

    @Test
    public void exists() {
        // Given
        Event event = EntitiesMother.randomEvent();
        doReturn(true)
                .when(jpaEventRepository)
                .existsById(event.getId().getNumber());

        // When
        boolean actual = repository.exists(event);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    public void save() {
        // Given
        Event unsaved = EntitiesMother.randomEvent();
        Event saved = EntitiesMother.randomEvent();
        doReturn(EventData.from(saved))
                .when(jpaEventRepository)
                .save(eq(EventData.from(unsaved)));

        // When
        Event actual = repository.persist(unsaved);

        // Then
        assertThat(actual).isEqualTo(saved);
    }

    @Test
    public void deleteAll() {
        // Given
        repository.deleteAll();

        // Then
        verify(jpaEventRepository).deleteAll();
    }

    @Test
    public void getAll() throws Exception {
        // Given
        List<Event> expected = EntitiesMother.randomEvents();
        List<EventData> dataEvents = expected.stream().map(EventData::from).collect(toList());
        doReturn(dataEvents)
                .when(jpaEventRepository)
                .findAllByOrderByIdAsc();

        // When
        List<Event> actual = repository.getAll();

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getAllByActorOrderByIdAsc() throws Exception {
        // Given
        Identity identity = EntitiesMother.randomIdentity();
        List<Event> expected = EntitiesMother.randomEvents();
        List<EventData> toBeReturned = expected.stream().map(EventData::from).collect(toList());
        doReturn(toBeReturned)
                .when(jpaEventRepository)
                .findByActorId(eq(identity.getNumber()), eq(new Sort(Sort.Direction.DESC, "id")));

        // When
        List<Event> actual = repository.getAllByActorIdOrderById(identity);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getAllByActorOrderByIdDesc() throws Exception {
        // Given
        Actor actor = EntitiesMother.randomActor();
        Identity id = actor.getId();
        List<Event> expected = EntitiesMother.randomEvents();
        List<EventData> toBeReturned = expected.stream().map(EventData::from).collect(toList());

        doReturn(toBeReturned)
                .when(jpaEventRepository)
                .findByActorId(eq(id.getNumber()), eq(new Sort(Sort.Direction.DESC, "createdAt")));

        // When
        Actor actual = repository.getAllByActorOrderByCreatedAt(actor);

        // Then
        assertThat(actual.getEvents()).isEqualTo(expected);
        assertThat(actual).isEqualToComparingOnlyGivenFields(actor, "id", "login", "avatar");
    }
}