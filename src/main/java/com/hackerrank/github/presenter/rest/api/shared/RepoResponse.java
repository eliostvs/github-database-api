package com.hackerrank.github.presenter.rest.api.shared;

import com.hackerrank.github.core.entities.Repo;
import lombok.Value;

@Value
public class RepoResponse {
    private final Long id;
    private final String name;
    private final String url;

    public static RepoResponse from(Repo repo) {
        return new RepoResponse(
                repo.getId().getNumber(),
                repo.getName(),
                repo.getUrl().toString()
        );
    }
}
