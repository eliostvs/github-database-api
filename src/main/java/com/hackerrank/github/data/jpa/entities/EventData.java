package com.hackerrank.github.data.jpa.entities;

import com.hackerrank.github.core.domain.Event;
import com.hackerrank.github.core.domain.EventType;
import com.hackerrank.github.core.domain.Identity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Entity(name = "Event")
@EqualsAndHashCode(of = {"id", "type", "createdAt"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "event")
public class EventData {
    @Id
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType type;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JoinColumn(name = "actor_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private ActorData actor;

    @JoinColumn(name = "repo_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private RepoData repo;

    public static EventData from(Event event) {
        EventData instance = new EventData(
                event.getId().getNumber(),
                event.getType(),
                event.getCreatedAt(),
                null,
                null
        );

        ActorData.from(event.getActor()).addEvent(instance);
        RepoData.from(event.getRepo()).addEvent(instance);

        return instance;
    }

    public Event fromThis() {
        return Event.builder()
                .id(new Identity(id))
                .type(type)
                .actor(actor.fromThis())
                .repo(repo.fromThis())
                .createdAt(createdAt)
                .build();
    }
}
