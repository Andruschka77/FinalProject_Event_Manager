package dev.sorokin.eventnotificator.config;

import dev.sorokin.eventcommon.kafka.EventChangeKafkaMessage;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class KafkaConfiguration {

    @Bean
    public ConsumerFactory<Long, EventChangeKafkaMessage> consumerFactory(
            KafkaProperties kafkaProperties
    ) {
        var props = kafkaProperties.buildConsumerProperties(new DefaultSslBundleRegistry());
        var factory = new DefaultKafkaConsumerFactory<Long, EventChangeKafkaMessage>(props);
        factory.setValueDeserializer(new JsonDeserializer<>(EventChangeKafkaMessage.class, false));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, EventChangeKafkaMessage> kafkaListenerContainerFactory(
            ConsumerFactory<Long, EventChangeKafkaMessage> consumerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Long, EventChangeKafkaMessage>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

}