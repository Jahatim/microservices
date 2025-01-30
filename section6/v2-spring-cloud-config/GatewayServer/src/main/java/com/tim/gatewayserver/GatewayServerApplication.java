package com.tim.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

    @Bean
    public RouteLocator customRoute(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route(p -> p.path("/bank/accounts/**").filters(f -> f.rewritePath("/bank/accounts/(?<service>.*)", "/${service}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("accounts-service-cb").setFallbackUri("forward:/fallback"))
                        )
                        .uri("lb://ACCOUNTS"))
                .route(p -> p.path("/bank/loans/**").filters(f -> f.rewritePath("/bank/loans/(?<service>.*)", "/${service}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .retry(retryConfig -> {
                                    retryConfig.setRetries(3).setMethods(HttpMethod.GET)
                                            .setBackoff(Duration.ofSeconds(1), Duration.ofSeconds(2), 2, true);
                                }))
                        .uri("lb://LOANS"))
                .route(p -> p.path("/bank/cards/**").filters(f -> f.rewritePath("/bank/cards/(?<service>.*)", "/${service}")
                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())).uri("lb://CARDS"))
                .build();


    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
    }
}
