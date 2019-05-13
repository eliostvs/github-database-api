package com.hackerrank.github.core.entities;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EventType {
    PUSH_EVENT("PushEvent");

    private final String str;

    @Override
    public String toString() {
        return str;
    }

    public static EventType from(String name) {
        if (PUSH_EVENT.str.equals(name)) {
            return PUSH_EVENT;
        } else {
            String errorMessage = String.format("Conversion failed because the value '%s' is not a valid event type", name);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
