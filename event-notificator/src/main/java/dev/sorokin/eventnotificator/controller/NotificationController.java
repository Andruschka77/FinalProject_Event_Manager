package dev.sorokin.eventnotificator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Test Notification access");
    }

    @PostMapping
    public ResponseEntity<String> test1() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Test Notification access");
    }

}