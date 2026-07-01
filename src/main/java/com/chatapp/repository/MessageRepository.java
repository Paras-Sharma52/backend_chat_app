package com.chatapp.repository;

import com.chatapp.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    
    @Query("{'$or': [{'senderId': ?0, 'receiverId': ?1}, {'senderId': ?1, 'receiverId': ?0}]}")
    Page<Message> findMessagesBetweenUsers(String user1Id, String user2Id, Pageable pageable);
    
    @Query("{'chatRoomId': ?0}")
    Page<Message> findByChatRoomId(String chatRoomId, Pageable pageable);
    
    @Query("{'receiverId': ?0, 'isRead': false}")
    List<Message> findUnreadMessagesByReceiverId(String receiverId);
    
    @Query("{'senderId': ?0, 'timestamp': {$gte: ?1}}")
    List<Message> findMessagesBySenderIdAndTimestampAfter(String senderId, LocalDateTime timestamp);
    
    @Query("{'$or': [{'senderId': ?0}, {'receiverId': ?0}]}")
    List<Message> findAllMessagesByUserId(String userId);
    
    long countByReceiverIdAndIsReadFalse(String receiverId);
} 