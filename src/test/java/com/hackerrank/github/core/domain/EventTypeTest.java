package com.hackerrank.github.core.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EventTypeTest {

    @Test
    public void toStringPushEvent() throws Exception {
        assertThat(EventType.PUSH_EVENT.toString()).isEqualTo("PushEvent");
    }

    @Test
    public void fromReturnsEnumWhenFound() throws Exception {
        assertThat(EventType.from("PushEvent")).isEqualTo(EventType.PUSH_EVENT);
    }

    @Test
    public void fromThrowsExceptionWhenNotFound() throws Exception {
        String name = EntitiesMother.randomName();
        String message = String.format("Conversion failed because the value '%s' is not a valid event type", name);

        assertThatThrownBy(() -> EventType.from(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }
}