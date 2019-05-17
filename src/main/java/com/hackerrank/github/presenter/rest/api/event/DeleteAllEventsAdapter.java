package com.hackerrank.github.presenter.rest.api.event;

import com.hackerrank.github.core.usecases.event.DeleteEventsUseCase;
import org.springframework.http.ResponseEntity;

public class DeleteAllEventsAdapter {
    public static ResponseEntity<Object> output(DeleteEventsUseCase.OutputValues ignored) {
        return ResponseEntity.ok().build();
    }
}
