package com.portfolio.central_server.controller;

import com.portfolio.central_server.DTO.UserDTO;
import com.portfolio.central_server.model.Survey;
import com.portfolio.central_server.model.User;
import com.portfolio.central_server.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

import static javax.crypto.Cipher.SECRET_KEY;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private AuthController authController;

    @Autowired
    public UserController(UserService userService, AuthController authController) {
        this.userService = userService;
        this.authController = authController;
    }

    // Endpoint to get all users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserDTO> usersListDTO = new ArrayList<UserDTO>();
        for(User u : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(u.getId());
            userDTO.setNickname(u.getNickname());
            usersListDTO.add(userDTO);
        }
        return ResponseEntity.ok(usersListDTO);
    }

    // Endpoint to get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        if(user.isPresent()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setNickname(user.get().getNickname());
            userDTO.setId(user.get().getId());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint to create a new user
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        Optional<User> userAlreadyExist = userService.findUserByNickname(user.getNickname());
        if(userAlreadyExist.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            User savedUser = userService.saveUser(user);
            String token = authController.authenticate(savedUser);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie", "jwt=" + token + "; HttpOnly; Path=/; Max-Age=604800");
            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();
        }
    }

    // Endpoint to delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        if(authorizationHeader != null) {
            // Extraer el token
            String token = authorizationHeader.substring(7);
            Optional<User> user = userService.findUserById(id);
            if (user.isPresent()) {
                if (authController.isAuthenticated(user.get(), token)) {
                    userService.deleteUserById(id);
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}