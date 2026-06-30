package dev.sorokin.eventmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {
        "dev.sorokin.eventmanager",
        "dev.sorokin.eventcommon"
})
public class EventManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagerApplication.class, args);
    }

}