package com.example.demo.service;

import com.example.demo.api.User;
import com.example.demo.client.WebJsonClient;
import com.example.demo.db.entity.UserEntity;
import com.example.demo.db.repository.UserRepository;
import com.example.demo.errors.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final Map<Long, User> users = new HashMap<>();
    private final WebJsonClient webJsonClient;
    private final UserRepository userRepository;

    @Autowired
    public UserService(WebJsonClient webJsonClient, UserRepository userRepository) {
        this.webJsonClient = webJsonClient;
        this.userRepository = userRepository;
        users.put(1L, new User("Wim"));
        users.put(2L, new User("Simon"));
        users.put(3L, new User("Siva"));
        users.put(4L, new User("Josh"));
    }

    public Mono<User> findUserByIdFromMap(Long userId) {
        User user = users.get(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        return Mono.just(user);
    }

    public Mono<User> findUserById(Long userId) {
        return webJsonClient.getUser(userId);
    }

    public Flux<User> findUsers() {
        return webJsonClient.getUsers();
    }

    public Flux<UserEntity> findUsersFromDatabase() {
        Flux<UserEntity> entities = userRepository.findAll();
        return entities;//.fromIterable(entities).map(this::map).subscribeOn(Schedulers.boundedElastic());
    }

    private User map(UserEntity entity) {
        return new User(entity.getName());
    }
}
