package com.hackerrank.github.core.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Event {
    private Identity id;
    private EventType type;
    private Actor actor;
    private Repo repo;
    private LocalDateTime createdAt;
}
