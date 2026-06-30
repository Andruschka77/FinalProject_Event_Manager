package dev.sorokin.eventnotificator.kafka;

import dev.sorokin.eventcommon.kafka.EventChangeKafkaMessage;
import dev.sorokin.eventnotificator.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaEventListener {

    private final NotificationService notificationService;

    public KafkaEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "event-changes-topic", containerFactory = "kafkaListenerContainerFactory")
    public void listenEvents(
        ConsumerRecord<Long, EventChangeKafkaMessage> record
    ) {
        log.info("Listen to the event information change: event={}", record.value());

        notificationService.saveEventChange(record.value());
    }

}