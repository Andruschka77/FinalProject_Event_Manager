package dev.sorokin.eventcommon.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("Entity='%s' с ID=%d не найдена", resourceName, id));
    }

    public ResourceNotFoundException(String resourceName, String loginFromToken) {
        super(String.format("Entity='%s' с login=%s не найдена", resourceName, loginFromToken));
    }

    public ResourceNotFoundException(String resourceName, Long userId, Long eventId) {
        super(String.format("Entity='%s' с userId='%d' и eventId='%d' не найдена", resourceName, userId, eventId));
    }

}