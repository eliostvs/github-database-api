package com.hackerrank.github.presenter.rest.api.actor;


import com.hackerrank.github.presenter.rest.api.shared.ActorRequest;
import com.hackerrank.github.presenter.rest.api.shared.ActorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/actors")
public interface ActorResource {

    @PutMapping
    CompletableFuture<ResponseEntity<Object>> create(@Valid @RequestBody ActorRequest request);

    @GetMapping
    CompletableFuture<List<ActorResponse>> getAll();

    @GetMapping("/streak")
    CompletableFuture<List<ActorResponse>> getStreak();
}
