package com.hackerrank.github.core.entities;

public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String entity, Identity id) {
        super(String.format("%s with ID '%d' was not found", entity, id.getNumber()));
    }
}
