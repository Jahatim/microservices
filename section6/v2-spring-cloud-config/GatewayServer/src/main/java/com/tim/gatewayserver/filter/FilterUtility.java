package com.tim.gatewayserver.filter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;


@Component
public class FilterUtility {

    public static final String CORRELATION_ID_HEADER = "timbank_traceid";

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FilterUtility.class);

    public boolean isCorrelationIdHeaderExists(HttpHeaders headers) {

        return StringUtils.isNotEmpty(headers.getFirst(CORRELATION_ID_HEADER));

    }

    public String getCorrelationId(HttpHeaders headers) {

        return StringUtils.isNotEmpty(headers.getFirst(CORRELATION_ID_HEADER)) ? headers.getFirst(CORRELATION_ID_HEADER) : null;

    }

    public ServerWebExchange setRequestHeaders(ServerWebExchange exchange, String name, String correlationId) {

        logger.info("Setting request header: {} with value: {}", name, correlationId);
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, correlationId).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        logger.info("Setting correlationId: {}", correlationId);

        exchange = setRequestHeaders(exchange, CORRELATION_ID_HEADER, correlationId);
        logger.info("Setting correlationId: {} completed for exchange: {}", correlationId, exchange);
        return exchange;

    }

}
