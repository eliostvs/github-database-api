package com.hackerrank.github.presenter.rest.api.common;

import com.hackerrank.github.core.domain.BusinessException;
import com.hackerrank.github.core.domain.EntitiesMother;
import com.hackerrank.github.core.domain.EntityNotFoundException;
import com.hackerrank.github.core.domain.Identity;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    public void handleDuplicateEventException() {
        // given
        BusinessException exception = new BusinessException("Error");

        // when
        ResponseEntity<ApiResponse> actual = exceptionHandler.handleBusinessErrors(exception);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(actual.getBody().getSuccess()).isFalse();
        assertThat(actual.getBody().getMessage()).isEqualTo("Error");
    }

    @Test
    public void handleEntityNotFound() {
        // given
        Identity identity = EntitiesMother.randomIdentity();
        EntityNotFoundException exception = new EntityNotFoundException("Actor", identity);

        // when
        ResponseEntity<ApiResponse> actual = exceptionHandler.handleNotFoundEntitiesErrors(exception);

        // Then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody().getSuccess()).isFalse();
    }
}