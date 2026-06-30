package dev.sorokin.eventmanager.kafka;

import dev.sorokin.eventcommon.kafka.EventChangeKafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaEventSender {

    private final KafkaTemplate<Long, EventChangeKafkaMessage> kafkaTemplate;

    public KafkaEventSender(
            KafkaTemplate<Long, EventChangeKafkaMessage> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(EventChangeKafkaMessage eventChangeKafkaMessage) {
        log.info("Sending event: event={}", eventChangeKafkaMessage);

        kafkaTemplate.send(
                "event-changes-topic",
                eventChangeKafkaMessage.eventId(),
                eventChangeKafkaMessage
        );
    }

}