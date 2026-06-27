package dev.sorokin.eventnotificator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "dev.sorokin.eventnotificator",
        "dev.sorokin.eventcommon"
})
public class EventNotificatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventNotificatorApplication.class, args);
    }

}
