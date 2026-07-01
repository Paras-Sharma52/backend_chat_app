package com.chatapp.controller;

import com.chatapp.model.Message;
import com.chatapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody Message message) {
        try {
            Message savedMessage = messageService.sendMessage(message);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Message sent successfully");
            response.put("data", savedMessage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/conversation/{user1Id}/{user2Id}")
    public ResponseEntity<?> getConversation(
            @PathVariable String user1Id,
            @PathVariable String user2Id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Page<Message> messages = messageService.getMessagesBetweenUsers(user1Id, user2Id, page, size);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/chatroom/{chatRoomId}")
    public ResponseEntity<?> getChatRoomMessages(
            @PathVariable String chatRoomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Page<Message> messages = messageService.getMessagesByChatRoom(chatRoomId, page, size);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{messageId}/read")
    public ResponseEntity<?> markAsRead(@PathVariable String messageId) {
        try {
            Message message = messageService.markMessageAsRead(messageId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Message marked as read");
            response.put("data", message);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/unread/{receiverId}")
    public ResponseEntity<?> getUnreadMessages(@PathVariable String receiverId) {
        try {
            return ResponseEntity.ok(messageService.getUnreadMessages(receiverId));
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/unread-count/{receiverId}")
    public ResponseEntity<?> getUnreadCount(@PathVariable String receiverId) {
        try {
            long count = messageService.getUnreadMessageCount(receiverId);
            Map<String, Object> response = new HashMap<>();
            response.put("unreadCount", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}