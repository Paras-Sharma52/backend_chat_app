package com.chatapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String email;
    private String password;
    private String fullName;
    private String profilePicture;
    private LocalDateTime createdAt;
    private LocalDateTime lastSeen;
    private boolean isOnline;
    private String currentToken;

    // Constructors
    public User() {
        this.createdAt = LocalDateTime.now();
        this.isOnline = false;
    }

    public User(String username, String email, String password, String fullName) {
        this();
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }
}