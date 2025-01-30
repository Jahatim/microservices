package com.tim.gatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
public class ResponseFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseFilter.class.getName());
    private final FilterUtility filterUtility;

    public ResponseFilter(FilterUtility filterUtility) {
        this.filterUtility = filterUtility;
    }

    @Bean
    public GlobalFilter postFilter() {

        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            LOGGER.info("in post filer corelation id update: {}", filterUtility.getCorrelationId(headers));
            HttpHeaders responseHeaders = exchange.getResponse().getHeaders();
            if (!responseHeaders.containsKey(FilterUtility.CORRELATION_ID_HEADER))
                responseHeaders.add(FilterUtility.CORRELATION_ID_HEADER, filterUtility.isCorrelationIdHeaderExists(headers) ? filterUtility.getCorrelationId(headers) : null);
        }));
    }
}
