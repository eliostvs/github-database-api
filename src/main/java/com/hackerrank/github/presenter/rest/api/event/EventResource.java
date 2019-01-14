package com.hackerrank.github.presenter.rest.api.event;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public interface EventResource {

    @DeleteMapping("/erase")
    CompletableFuture<ResponseEntity<Object>> deleteAll();

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    CompletableFuture<ResponseEntity<ResponseEntity<Object>>> create(@Valid @RequestBody EventRequest request, HttpServletRequest httpServletRequest);

    @GetMapping("/events")
    CompletableFuture<List<EventResponse>> getAll();

    @GetMapping("/events/actors/{id}")
    CompletableFuture<List<EventResponse>> getByActorId(@PathVariable("id") long id);
}
