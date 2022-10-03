package com.example.demo.service;

import com.example.demo.api.User;
import com.example.demo.client.WebJsonClient;
import com.example.demo.errors.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final Map<Long, User> users = new HashMap<>();
    private final WebJsonClient webJsonClient;

    @Autowired
    public UserService(WebJsonClient webJsonClient) {
        this.webJsonClient = webJsonClient;
        users.put(1L, new User("Wim"));
        users.put(2L, new User("Simon"));
        users.put(3L, new User("Siva"));
        users.put(4L, new User("Josh"));
    }

    public Mono<User> findUserById(Long userId) {
        User user = users.get(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        return Mono.just(user);
    }

    public Mono<User> findClientUserById(Long userId) {
        return webJsonClient.getUser(userId);
    }
}
