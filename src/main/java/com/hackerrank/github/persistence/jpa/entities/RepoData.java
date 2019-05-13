package com.hackerrank.github.persistence.jpa.entities;

import com.hackerrank.github.core.entities.Identity;
import com.hackerrank.github.core.entities.Repo;
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
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Builder
@Entity(name = "Repo")
@Getter
@NoArgsConstructor
@Setter
@Table(name = "repo")
@EqualsAndHashCode(of = {"id"})
public class RepoData {
    @Id
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @OneToMany(mappedBy = "repo")
    private Set<EventData> events = new HashSet<>();

    public void addEvent(EventData event) {
        if (events == null) {
            events = new HashSet<>();
        }

        event.setRepo(this);
        events.add(event);
    }

    public static RepoData from(Repo repo) {
        return new RepoData(
                repo.getId().getNumber(),
                repo.getName(),
                repo.getUrl().toString(),
                new HashSet<>()
        );
    }


    public Repo fromThis() {
        return new Repo(
                new Identity(id),
                name,
                URI.create(url)
        );
    }
}
