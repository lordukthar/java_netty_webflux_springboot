package com.example.demo.client;

import com.example.demo.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
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

     /*
    List<Integer> list = Arrays.asList(1, 3, 5);
      Flux.fromIterable(list)
          .subscribe(System.out::println);
     */
    public Flux<User> getUsers() {
        return jsonWebClient
                .get()
                .uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class);
    }
}
