package com.example.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebContextFilter implements WebFilter {
    private static final Logger LOG = LoggerFactory.getLogger("WebContextFilter");
    public static final String X_CUSTOM_HEADER = "X-Custom-Header";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("FOO Filter 2: WebContextFilter");

        StringBuilder sb = new StringBuilder();

        try {
            sb.append(exchange.getRequest().getHeaders().get("Authorization").get(0));
        } catch (Exception ex) {
            LOG.error("No Authorization header found, adding a new", ex);
            sb.append("Bearer " + UUID.randomUUID().toString());
        }


        exchange.getResponse()
                .getHeaders().add("web-filter", "web-filter-test");


        return chain.filter(exchange).contextWrite(Context.of("X-Custom-Header", "APA22 "))//"sb.toString()))
                .contextWrite(Context.of("Authorization", sb.toString()));


      /*  return chain.filter(exchange)
                .contextWrite(Context.of("X-Custom-Header", new ConcurrentHashMap<String, Object>()));*/
    }

/*
 @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("WebContextFilter");

        String string = exchange.getRequest().getHeaders().get("X-Custom-Header").get(0);
        ConcurrentHashMap apa = new ConcurrentHashMap<String, Object>();
        apa.put("X-Custom-Header", string);

        return chain.filter(exchange)
                .contextWrite(Context.of("X-Custom-Header",apa));
    }

 */

   /* @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        List<String> customHeaderValues = serverWebExchange.getRequest().getHeaders().get(X_CUSTOM_HEADER);
        String singleCustomHeader = customHeaderValues != null && customHeaderValues.size() == 1 ? customHeaderValues.get(0) : null;
        serverWebExchange.getResponse();
        return webFilterChain.filter(serverWebExchange).deferContextual(context -> {
            return singleCustomHeader != null ?

                    context.forEach(x -> x.);
                    context.stream().p(X_CUSTOM_HEADER, new String[] {singleCustomHeader}) : context;
        });
    }*/
}

