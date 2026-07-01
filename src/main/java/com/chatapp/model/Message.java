package com.chatapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    
    private String senderId;
    private String receiverId;
    private String content;
    private MessageType type;
    private LocalDateTime timestamp;
    private boolean isRead;
    private String chatRoomId;

    public enum MessageType {
        TEXT, IMAGE, FILE, AUDIO, VIDEO
    }

    // Constructors
    public Message() {
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
        this.type = MessageType.TEXT;
    }

    public Message(String senderId, String receiverId, String content) {
        this();
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
} 