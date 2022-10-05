package com.example.demo;

import com.example.demo.api.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

//@RestController
//@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getUsers() {
        return ResponseEntity.ok("fndsm,n");
    }

    @GetMapping("/{id}")
    public Mono<User> getUser(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }
}
