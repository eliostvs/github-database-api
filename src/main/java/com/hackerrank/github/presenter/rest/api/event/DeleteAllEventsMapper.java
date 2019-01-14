package com.hackerrank.github.presenter.rest.api.event;

import com.hackerrank.github.core.usecases.event.DeleteEventsUseCase;
import org.springframework.http.ResponseEntity;

public class DeleteAllEventsMapper {

    public static ResponseEntity<Object> output(DeleteEventsUseCase.OutputValues outputValues) {
        return ResponseEntity.ok().build();
    }
}
