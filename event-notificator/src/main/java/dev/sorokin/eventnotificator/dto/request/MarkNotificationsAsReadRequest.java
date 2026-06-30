package dev.sorokin.eventnotificator.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record MarkNotificationsAsReadRequest(

        @NotEmpty
        List<Long> notificationIds

) {
}