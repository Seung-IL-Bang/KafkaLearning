package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaProducerController {

    private final KafkaProducerService kafkaProducerService;

    @GetMapping("/publish")
    public String publish(String message) {
        kafkaProducerService.send(message);

        return "published a message : " + message;
    }

    @GetMapping("/publish2")
    public String publishWithCallback(String message) {
        kafkaProducerService.sendWithCallback(message);
        return "published a message with callback: " + message;
    }

    @GetMapping("/publish3")
    public String publishJson(MyMessage message) {
        kafkaProducerService.sendJson(message);
        return "published a message to Json: " + message.getName() + ", " + message.getMessage();
    }
}
