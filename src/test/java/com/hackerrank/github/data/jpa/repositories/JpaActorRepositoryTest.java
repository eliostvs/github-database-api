package com.hackerrank.github.data.jpa.repositories;

import com.hackerrank.github.data.jpa.entities.ActorData;
import com.hackerrank.github.data.jpa.entities.DataEntitiesMother;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaActorRepositoryTest {

    @Autowired
    private JpaActorRepository repository;

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
    public void findAllOrderByNumberEventsDesc() {
        // Given
        LocalDateTime today = DataEntitiesMother.today();
        LocalDateTime yesterday = DataEntitiesMother.yesterday();
        RepoData repoData = DataEntitiesMother.randomRepoData();

        // First - the bigger number of events
        ActorData first = DataEntitiesMother.randomActorData((actor) -> {
            actor.addEvent(DataEntitiesMother.randomEventData());
            actor.addEvent(DataEntitiesMother.randomEventData());
            return actor;
        });

        // Second - most recent
        ActorData second = DataEntitiesMother.randomActorData((actor) -> {
            actor.addEvent(
                    DataEntitiesMother.randomEventData(event -> {
                        event.setCreatedAt(today);
                        return event;
                    })
            );
            return actor;
        });

        // Third - alphabetically
        ActorData third = DataEntitiesMother.randomActorData((actor) -> {
            actor.setLogin("a");

            actor.addEvent(
                    DataEntitiesMother.randomEventData(event -> {
                        event.setCreatedAt(yesterday);
                        return event;
                    })
            );
            return actor;
        });

        // Last - alphabetically
        ActorData last = DataEntitiesMother.randomActorData((actor) -> {
            actor.setLogin("b");

            actor.addEvent(
                    DataEntitiesMother.randomEventData(event -> {
                        event.setCreatedAt(yesterday);
                        return event;
                    })
            );

            return actor;
        });

        Stream.of(
                first.getEvents().stream(),
                second.getEvents().stream(),
                third.getEvents().stream(),
                last.getEvents().stream()
        ).flatMap(s -> s).forEach(event -> {
            repoData.addEvent(event);
            entityManager.persistAndFlush(event);
        });

        // when
        List<ActorData> actual = repository.findAllOrderByLargestNumberEvents();

        // then
        List<Long> expectedOrderIds = Arrays.asList(first.getId(), second.getId(), third.getId(), last.getId());
        assertThat(actual).extracting("id").isEqualTo(expectedOrderIds);
    }

    @Test
    public void findAllOrderByStreak() throws Exception {
        // given
        LocalDateTime today = DataEntitiesMother.today();
        LocalDateTime yesterday = DataEntitiesMother.yesterday();

        // First - most streaks
        ActorData first = DataEntitiesMother.randomActorData(actor -> {
            actor.setStreak(2);
            return actor;
        });

        // Second - most recent
        ActorData second = DataEntitiesMother.randomActorData(actor -> {
            actor.setStreak(1);
            actor.setLatestEvent(today);
            return actor;
        });

        // Third - alphabetically first
        ActorData third = DataEntitiesMother.randomActorData(actor -> {
            actor.setStreak(1);
            actor.setLatestEvent(yesterday);
            actor.setLogin("a");
            return actor;
        });

        // Last - alphabetically last
        ActorData last = DataEntitiesMother.randomActorData(actor -> {
            actor.setStreak(1);
            actor.setLatestEvent(yesterday);
            actor.setLogin("b");
            return actor;
        });

        Arrays.asList(first, second, third, last).forEach(actor ->
                entityManager.persistAndFlush(actor)
        );

        // when
        List<ActorData> actual = repository.findAllOrderByStreak();

        // then
        List<Long> expected = Arrays.asList(first.getId(), second.getId(), third.getId(), last.getId());

        assertThat(actual).extracting("id").isEqualTo(expected);
    }
}