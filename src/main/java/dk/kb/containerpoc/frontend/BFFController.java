package dk.kb.containerpoc.division;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@ConditionalOnExpression("${app.isBFF}")
@RestController
public class BFFController {
    private final WebClient webClient;

    @Autowired
    public BFFController(WebClient webClient) {
        this.webClient = webClient;
    }

    @PostMapping("/division")
    public int division(@RequestBody dk.kb.containerpoc.division.DivisionBody json) {
        Mono<Integer> result = webClient.post()
                .uri("/division")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(Integer.class);
                    } else {
                        return response.createException().flatMap(Mono::error); // TODO need to throw exception and use exception-handler?
                    }
                });
        return result.block(Duration.ofSeconds(30));
    }
}
