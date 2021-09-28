package com.app.apigateway.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * @author t0k02w6 on 28/09/21
 * @project ms-api-gateway
 */
@Configuration
public class MsApiGatewayConfiguration {
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        Function<PredicateSpec, Buildable<Route>> routeFunction =
                p -> p.path("/get")
                        .filters(f -> f.addRequestHeader("MyHeader", "MyURI")
                        .addRequestParameter("MyParam", "MyValue"))
                        .uri("http://httpbin.org:80");
        return builder.routes()
                .route(routeFunction)
                .route(p -> p.path("/currency-exchange/**").uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**").uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-feign/**").uri("lb://currency-conversion-feign"))
                .build();
    }
}
