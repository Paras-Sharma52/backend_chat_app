package com.chatapp.service;

import com.chatapp.model.User;
import com.chatapp.repository.UserRepository;
import com.chatapp.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageRepository messageRepository;

    public User createUser(User user) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public List<User> searchUsersByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    public List<User> getOnlineUsers() {
        return userRepository.findByIsOnline(true);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUserStatus(String userId, boolean isOnline) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setOnline(isOnline);
            user.setLastSeen(LocalDateTime.now());
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    public User updateUserProfile(String userId, User updatedUser) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setFullName(updatedUser.getFullName());
            user.setProfilePicture(updatedUser.getProfilePicture());
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    public boolean validateUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    public void updateUserToken(String username, String token) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setCurrentToken(token);
            userRepository.save(user);
        }
    }

    /**
     * Returns a list of users the given user has chatted with (contacts).
     */
    public List<User> getContactsForUser(String userId) {
        List<com.chatapp.model.Message> messages = messageRepository.findAllMessagesByUserId(userId);
        Set<String> contactIds = new HashSet<>();
        for (com.chatapp.model.Message msg : messages) {
            if (!msg.getSenderId().equals(userId)) {
                contactIds.add(msg.getSenderId());
            }
            if (!msg.getReceiverId().equals(userId)) {
                contactIds.add(msg.getReceiverId());
            }
        }
        if (contactIds.isEmpty())
            return List.of();
        return userRepository.findAllById(contactIds);
    }

    /**
     * Returns a map with all users and contacts for a user.
     */
    public Map<String, Object> getAllUsersAndContacts(String userId) {
        List<User> allUsers = userRepository.findAll();
        List<User> contacts = getContactsForUser(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("allUsers", allUsers);
        result.put("contacts", contacts);
        return result;
    }
}