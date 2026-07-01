package com.chatapp.repository;

import com.chatapp.model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    
    @Query("{'participants': ?0}")
    List<ChatRoom> findByParticipantId(String participantId);
    
    @Query("{'participants': {$all: ?0}}")
    List<ChatRoom> findByParticipantsContainingAll(List<String> participantIds);
    
    @Query("{'createdBy': ?0}")
    List<ChatRoom> findByCreatedBy(String createdBy);
    
    @Query("{'isGroupChat': ?0}")
    List<ChatRoom> findByIsGroupChat(boolean isGroupChat);
    
    @Query("{'name': {$regex: ?0, $options: 'i'}}")
    List<ChatRoom> findByNameContainingIgnoreCase(String name);
} 