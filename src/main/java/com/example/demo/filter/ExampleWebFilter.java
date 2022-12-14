package com.example.demo.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class ExampleWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange,
                             WebFilterChain webFilterChain) {

        System.out.println("FOO Filter 1: ExampleWebFilter");

        serverWebExchange.getResponse()
                .getHeaders().add("web-filter", "web-filter-test");
        return webFilterChain.filter(serverWebExchange);
    }
}


