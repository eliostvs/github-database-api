package com.hackerrank.github.data.jpa.repositories;

import com.hackerrank.github.data.jpa.entities.ActorData;
import com.hackerrank.github.data.jpa.entities.DataEntitiesMother;
import com.hackerrank.github.data.jpa.entities.EventData;
import com.hackerrank.github.data.jpa.entities.RepoData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaEventRepositoryTest {

    @Autowired
    private JpaEventRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @AutoConfigurationPackage
    @Configuration
    @EntityScan("com.hackerrank.github.data.jpa.entities")
    static class Config {
    }

    @Before
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void deleteEventShouldDeleteAllRelatedRepoAndActor() throws Exception {
        // given
        ActorData actor = DataEntitiesMother.randomActorData();
        RepoData repo = DataEntitiesMother.randomRepoData();

        EventData event = DataEntitiesMother.randomEventData();

        repo.addEvent(event);
        actor.addEvent(event);

        repository.save(event);
        // when
        repository.deleteAll();

        // then
        assertThat(entityManager.find(RepoData.class, repo.getId())).isNull();
        assertThat(entityManager.find(ActorData.class, actor.getId())).isNull();
    }

    @Test
    public void saveEvent() throws Exception {
        // given
        ActorData actor = DataEntitiesMother.randomActorData();
        RepoData repo = DataEntitiesMother.randomRepoData();

        EventData event = DataEntitiesMother.randomEventData();

        repo.addEvent(event);
        actor.addEvent(event);

        // when
        repository.save(event);

        // then
        assertThat(entityManager.find(ActorData.class, actor.getId())).isEqualTo(actor);
    }

    @Test
    public void findByActorIdOrderByIdAsc() {
        // given
        ActorData actorData = DataEntitiesMother.randomActorData(actor -> {
            DataEntitiesMother.randomEventsData().forEach(actor::addEvent);
            return actor;
        });

        ActorData otherActor = DataEntitiesMother.randomActorData(actor -> {
            DataEntitiesMother.randomEventsData().forEach(actor::addEvent);
            return actor;
        });

        RepoData repo = DataEntitiesMother.randomRepoData();

        Stream.concat(
                actorData.getEvents().stream(), otherActor.getEvents().stream()
        ).forEach(event -> {
            repo.addEvent(event);
            entityManager.persistAndFlush(event);
        });

        // when
        List<EventData> actual = repository.findByActorIdOrderByIdAsc(actorData.getId());

        // then
        List<Long> expectedOrderIds = actorData
                .getEvents()
                .stream()
                .map(EventData::getId)
                .sorted()
                .collect(toList());

        assertThat(actual).extracting("id").isEqualTo(expectedOrderIds);
    }

    @Test
    public void findAllByOrderByIdAsc() {
        // given
        ActorData actorData = DataEntitiesMother.randomActorData(actor -> {
            actor.addEvent(DataEntitiesMother.randomEventData());
            actor.addEvent(DataEntitiesMother.randomEventData());

            return actor;
        });

        RepoData repo = DataEntitiesMother.randomRepoData();

        actorData.getEvents().forEach(event -> {
            repo.addEvent(event);
            entityManager.persistAndFlush(event);
        });

        // when
        List<EventData> actual = repository.findAllByOrderByIdAsc();

        // then
        List<Long> expectedOrderIds = actorData.getEvents().stream()
                .map(EventData::getId)
                .sorted()
                .collect(toList());

        assertThat(actual).extracting("id").isEqualTo(expectedOrderIds);
    }

    @Test
    public void findByActorIdOrderById() {
        // given
        ActorData actorData = DataEntitiesMother.randomActorData(actor -> {
            actor.addEvent(DataEntitiesMother.randomEventData(event -> {
                event.setId(1L);
                return event;
            }));

            actor.addEvent(DataEntitiesMother.randomEventData(event -> {
                event.setId(2L);
                return event;
            }));

            return actor;
        });

        RepoData repo = DataEntitiesMother.randomRepoData();

        actorData.getEvents().forEach(event -> {
            repo.addEvent(event);
            entityManager.persistAndFlush(event);
        });

        // when
        List<EventData> actual = repository.findByActorId(actorData.getId(), new Sort(Sort.Direction.ASC, "id"));

        // then
        assertThat(actual).extracting("id").isEqualTo(Arrays.asList(1L, 2L));
    }

    @Test
    public void findByActorIdOrderByLatestDate() {
        // given
        LocalDateTime today = DataEntitiesMother.today();
        LocalDateTime yesterday = DataEntitiesMother.yesterday();

        ActorData actorData = DataEntitiesMother.randomActorData(actor -> {
            actor.addEvent(DataEntitiesMother.randomEventData(event -> {
                event.setId(1L);
                event.setCreatedAt(yesterday);
                return event;
            }));

            actor.addEvent(DataEntitiesMother.randomEventData(event -> {
                event.setId(2L);
                event.setCreatedAt(today);
                return event;
            }));

            return actor;
        });

        RepoData repo = DataEntitiesMother.randomRepoData();

        actorData.getEvents().forEach(event -> {
            repo.addEvent(event);
            entityManager.persistAndFlush(event);
        });

        // when
        List<EventData> actual = repository.findByActorId(actorData.getId(), new Sort(Sort.Direction.DESC, "createdAt"));

        // then
        assertThat(actual).extracting("id").isEqualTo(Arrays.asList(2L, 1L));
    }
}