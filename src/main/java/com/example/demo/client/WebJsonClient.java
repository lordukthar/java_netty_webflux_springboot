package com.example.demo.client;

import com.example.demo.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WebJsonClient {

    //https://jsonplaceholder.typicode.com/users

    private final WebClient jsonWebClient;

    @Autowired
    public WebJsonClient(WebClient jsonWebClient) {
        this.jsonWebClient = jsonWebClient;
    }

    public Mono<User> getUser(Long id) {
        return jsonWebClient
                .get()
                .uri("/users/"+id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class);
    }
}
