package dev.sorokin.eventmanager.config;

import dev.sorokin.eventcommon.kafka.EventChangeKafkaMessage;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfiguration {

    @Bean
    public KafkaTemplate<Long, EventChangeKafkaMessage> kafkaTemplate(
            KafkaProperties kafkaProperties
    ) {
        var props = kafkaProperties.buildProducerProperties(new DefaultSslBundleRegistry());
        ProducerFactory<Long, EventChangeKafkaMessage> producerFactory = new DefaultKafkaProducerFactory<>(props);
        return new KafkaTemplate<>(producerFactory);
    }

}