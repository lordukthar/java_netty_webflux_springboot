package com.example.demo;

import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(UserController.class)
@Import(UserService.class)
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testUserNotFound() {
        webTestClient.get()
                .uri("/users/10")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .consumeWith(System.out::println);
                //.jsonPath("$.code").isEqualTo("USER_NOT_FOUND")
                //.jsonPath("$.message").isEqualTo("No user found for id 10")
                //.jsonPath("$.userId").isEqualTo(10L);

    }
}