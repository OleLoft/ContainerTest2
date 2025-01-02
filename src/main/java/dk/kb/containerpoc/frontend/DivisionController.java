package dk.kb.containerpoc.division;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@ConditionalOnExpression("!${app.isBFF}")
@RestController
public class DivisionController {
    private final WebClient webClient;

    @Autowired
    public DivisionController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/division")
    public int division(@RequestParam int num1, @RequestParam int num2, @RequestParam(required = false) String sessionId) {
        return num1 / num2;
    }
}
