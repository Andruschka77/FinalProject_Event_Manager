package dev.sorokin.eventmanager.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("Entity='%s' с ID=%d не найдена", resourceName, id));
    }

}