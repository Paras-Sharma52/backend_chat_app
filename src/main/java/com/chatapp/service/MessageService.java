package com.chatapp.service;

import com.chatapp.model.Message;
import com.chatapp.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    public Message sendMessage(Message message) {
        return messageRepository.save(message);
    }
    
    public Page<Message> getMessagesBetweenUsers(String user1Id, String user2Id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return messageRepository.findMessagesBetweenUsers(user1Id, user2Id, pageable);
    }
    
    public Page<Message> getMessagesByChatRoom(String chatRoomId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return messageRepository.findByChatRoomId(chatRoomId, pageable);
    }
    
    public List<Message> getUnreadMessages(String receiverId) {
        return messageRepository.findUnreadMessagesByReceiverId(receiverId);
    }
    
    public Message markMessageAsRead(String messageId) {
        Optional<Message> messageOpt = messageRepository.findById(messageId);
        if (messageOpt.isPresent()) {
            Message message = messageOpt.get();
            message.setRead(true);
            return messageRepository.save(message);
        }
        throw new RuntimeException("Message not found");
    }
    
    public void markAllMessagesAsRead(String receiverId, String senderId) {
        List<Message> unreadMessages = messageRepository.findUnreadMessagesByReceiverId(receiverId);
        for (Message message : unreadMessages) {
            if (message.getSenderId().equals(senderId)) {
                message.setRead(true);
                messageRepository.save(message);
            }
        }
    }
    
    public long getUnreadMessageCount(String receiverId) {
        return messageRepository.countByReceiverIdAndIsReadFalse(receiverId);
    }
    
    public List<Message> getRecentMessages(String userId, LocalDateTime since) {
        return messageRepository.findMessagesBySenderIdAndTimestampAfter(userId, since);
    }
    
    public Optional<Message> findById(String messageId) {
        return messageRepository.findById(messageId);
    }
    
    public void deleteMessage(String messageId) {
        messageRepository.deleteById(messageId);
    }
} 