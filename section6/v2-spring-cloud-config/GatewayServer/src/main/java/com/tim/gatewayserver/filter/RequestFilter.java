package com.tim.gatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Order(1)
@Component
public class RequestFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);

    private final FilterUtility filterUtility;

    public RequestFilter(FilterUtility filterUtility) {
        this.filterUtility = filterUtility;

    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        HttpHeaders headers = exchange.getRequest().getHeaders();

        if (filterUtility.isCorrelationIdHeaderExists(headers)) {
            logger.info("CorrelationId: {}", filterUtility.getCorrelationId(headers));
        } else {
            exchange = filterUtility.setCorrelationId(exchange, generateCorrelationId());
            logger.info("CorrelationId generated: {}", filterUtility.getCorrelationId(exchange.getRequest().getHeaders()));
        }


        return chain.filter(exchange);
    }

    private String generateCorrelationId() {

        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
