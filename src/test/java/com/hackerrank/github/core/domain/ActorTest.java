package com.hackerrank.github.core.domain;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ActorTest {

    @Test
    public void validateUpdateReturnSuccessWhenBlockedFieldsAreNoChanged() throws Exception {
        // given
        Actor actor = EntitiesMother.randomActor();
        Actor otherActor = EntitiesMother.copyWithField(Actor.class, actor, "avatar", "avatar");

        // when
        Actor.UpdateValidation actual = actor.validateUpdate(otherActor);

        // then
        assertThat(actual).isEqualTo(Actor.UpdateValidation.success());
    }

    @Test
    public void validateUpdateReturnErrorWhenBlockedFieldsAreChanged() throws Exception {
        // given
        Actor actor = EntitiesMother.randomActor();
        Actor otherActor = EntitiesMother.copyWithField(Actor.class, actor, "login", "login");

        // when
        Actor.UpdateValidation actual = actor.validateUpdate(otherActor);

        // then
        assertThat(actual.isSuccess()).isFalse();

        String expected = String.format("Update of the Actor with ID '%d' failed because only field 'avatar' can be change. Tried to change the field 'login'", actor.getId().getNumber());
        assertThat(actual.getReason()).isEqualTo(expected);
    }

    @Test
    public void calculateStreak() throws Exception {
        // given

        // First event - ten days ago
        Event firstEvent = EntitiesMother.randomEvent(instance -> {
            instance.setCreatedAt(EntitiesMother.xDaysAgo(10));
            return instance;
        });

        // Second event - three days ago - don't complete a streak
        Event secondEvent = EntitiesMother.randomEvent(instance -> {
            instance.setCreatedAt(EntitiesMother.xDaysAgo(3));
            return instance;
        });

        // Third event - yesterday - don't complete a streak
        Event thirdEvent = EntitiesMother.randomEvent(instance -> {
            instance.setCreatedAt(EntitiesMother.yesterday());
            return instance;
        });

        // Forth event - today - last event and one day streak
        Event forthEvent = EntitiesMother.randomEvent(instance -> {
            instance.setCreatedAt(EntitiesMother.today());
            return instance;
        });

        Actor actor = EntitiesMother.randomActor(instance -> {
            instance.setEvents(
                    Arrays.asList(forthEvent, thirdEvent, secondEvent, firstEvent));

            return instance;
        });

        // when
        actor.calculateStreak();

        // then
        assertThat(actor.getLatestEvent()).isEqualTo(EntitiesMother.today());
        assertThat(actor.getStreak()).isEqualTo(1);
    }
}