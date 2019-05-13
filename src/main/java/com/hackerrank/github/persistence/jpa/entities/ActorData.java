package com.hackerrank.github.persistence.jpa.entities;

import com.hackerrank.github.core.entities.Actor;
import com.hackerrank.github.core.entities.Identity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Entity(name = "Actor")
@EqualsAndHashCode(of = {"id"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "actor")
public class ActorData {
    @Id
    private Long id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String avatar;

    @OneToMany(mappedBy = "actor")
    private List<EventData> events = new ArrayList<>();

    @Column(columnDefinition = "int default 0")
    private int streak = 0;

    @Column(name = "latest_event")
    private LocalDateTime latestEvent;

    public void addEvent(EventData event) {
        if (events == null) {
            events = new ArrayList<>();
        }

        event.setActor(this);
        events.add(event);
    }

    public static ActorData from(Actor actor) {
        return ActorData.builder()
                .id(actor.getId().getNumber())
                .login(actor.getLogin())
                .avatar(actor.getAvatar())
                .streak(actor.getStreak())
                .latestEvent(actor.getLatestEvent())
                .build();
    }

    public Actor fromThis() {
        return Actor.builder()
                .id(new Identity(id))
                .login(login)
                .avatar(avatar)
                .streak(streak)
                .latestEvent(latestEvent)
                .build();
    }
}
