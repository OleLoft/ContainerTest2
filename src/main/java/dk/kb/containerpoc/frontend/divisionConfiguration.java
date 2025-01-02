package dk.kb.containerpoc.division;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class divisionConfiguration {
    @Value("${service.endpoint}")
    private String backendEndpoint;

    @Bean
    public WebClient webClient() { // Default timeout is 30 sec so need to configure if you need more
        return WebClient.create(backendEndpoint);
    }

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA: ");
        return filter;
    }
}