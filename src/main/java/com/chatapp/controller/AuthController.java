package com.chatapp.controller;

import com.chatapp.config.JwtUtil;
import com.chatapp.model.User;
import com.chatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("user", createdUser);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        if (userService.validateUser(username, password)) {
            String token = jwtUtil.generateToken(username);
            userService.updateUserToken(username, token);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("username", username);
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid credentials");
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users-and-contacts/{userId}")
    public ResponseEntity<?> getAllUsersAndContacts(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getAllUsersAndContacts(userId));
    }
}