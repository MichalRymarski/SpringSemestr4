package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.DeleteState;
import org.example.dto.CreateUserDto;
import org.example.dto.UserDto;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Collection<UserDto>> getUsers() {
        Collection<UserDto> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("{login}")
     public ResponseEntity<UserDto> getUser(@PathVariable String login) {
//    @GetMapping
//    public ResponseEntity<UserDto> getUser(@RequestParam(required = true) String login) {
        UserDto userDto = userService.getUser(login);
        if (userDto != null) {
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserDto createUserDto) {
        boolean success = userService.createUser(createUserDto);
        if (success) {
            return ResponseEntity.ok(("User created successfully"));
        }else{
            return ResponseEntity.badRequest().body("nie udalo sie dodac usera");
        }
    }

    @DeleteMapping("{login}")
    public ResponseEntity<?> deleteUser(@PathVariable String login) {
        DeleteState result = userService.deleteUser(login);
        switch (result) {
            case NOT_FOUND -> {
                return ResponseEntity.notFound().build();
            }
            case RENTED -> {
                return ResponseEntity.badRequest().body(result);
            }
            default -> {
                return ResponseEntity.ok().body(result);
            }
        }
    }


}