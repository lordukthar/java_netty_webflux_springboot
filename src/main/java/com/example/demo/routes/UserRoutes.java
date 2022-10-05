package com.example.demo.routes;

import com.example.demo.api.User;
import com.example.demo.db.entity.UserEntity;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class UserRoutes {

    private final UserService userService;

    @Autowired
    public UserRoutes(UserService userService) {
        this.userService = userService;
    }


    @Bean
    RouterFunction<ServerResponse> getUserByIdRoute() {
        return route(GET("/users/{id}"),
                req -> ok().body(
                        userService.findUserById(Long.valueOf(req.pathVariable("id"))), User.class));
    }

    @Bean
    RouterFunction<ServerResponse> getUserByIdFromMapRoute() {
        return route(GET("/users/map/{id}"),
                req -> ok().body(
                        userService.findUserByIdFromMap(100L), User.class));
    }

    @Bean
    RouterFunction<ServerResponse> getUsers() {
        return route(GET("/users"),
                req -> ok().body(
                        userService.findUsers(), User.class));
    }

    @Bean
    RouterFunction<ServerResponse> getUsersFromDB() {
        return route(GET("/users-db"),
                req -> ok().body(
                        userService.findUsersFromDatabase(), User.class));
    }



}
