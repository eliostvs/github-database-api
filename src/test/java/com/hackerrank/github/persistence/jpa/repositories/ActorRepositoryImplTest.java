package com.hackerrank.github.persistence.jpa.repositories;

import com.hackerrank.github.core.entities.Actor;
import com.hackerrank.github.core.entities.EntitiesMother;
import com.hackerrank.github.core.entities.Identity;
import com.hackerrank.github.persistence.jpa.entities.ActorData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ActorRepositoryImplTest {

    @InjectMocks
    private ActorRepositoryImpl repository;

    @Mock
    private JpaActorRepository jpaRepository;

    @Test
    public void exists() {
        // given
        Identity identity = EntitiesMother.randomIdentity();

        doReturn(true)
                .when(jpaRepository)
                .existsById(eq(identity.getNumber()));

        // then
        assertThat(repository.exists(identity)).isTrue();
    }

    @Test
    public void update() {
        Actor input = EntitiesMother.randomActor();
        Actor expected = EntitiesMother.randomActor();

        doReturn(ActorData.from(expected))
                .when(jpaRepository)
                .save(eq(ActorData.from(input)));

        // when
        Actor actual = repository.update(input);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getByIdentity() {
        // given
        Actor actor = EntitiesMother.randomActor();

        doReturn(Optional.of(ActorData.from(actor)))
                .when(jpaRepository)
                .findById(eq(actor.getId().getNumber()));

        // when
        Optional<Actor> actorOptional = repository.findById(actor.getId());

        // then
        assertThat(actorOptional).isEqualTo(Optional.of(actor));
    }

    @Test
    public void getAll() {
        // given
        List<Actor> expected = EntitiesMother.randomActors();
        List<ActorData> toBeReturned = expected
                .stream()
                .map(ActorData::from)
                .collect(toList());

        doReturn(toBeReturned)
                .when(jpaRepository)
                .findAllOrderByLargestNumberEvents();

        // then
        assertThat(repository.getAll()).isEqualTo(expected);
    }

    @Test
    public void getAllByStreak() throws Exception {
        // given
        List<Actor> expected = EntitiesMother.randomActors();
        List<ActorData> toBeReturned = expected
                .stream()
                .map(ActorData::from)
                .collect(toList());

        doReturn(toBeReturned)
                .when(jpaRepository)
                .findAllOrderByStreak();

        // then
        assertThat(repository.getStreak()).isEqualTo(expected);
    }

    @Test
    public void persist() throws Exception {
        // given
        Actor actor = EntitiesMother.randomActor();

        // when
        repository.persist(actor);

        // then
        verify(jpaRepository).save(eq(ActorData.from(actor)));
    }
}