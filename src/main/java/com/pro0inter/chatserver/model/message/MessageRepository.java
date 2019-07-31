package com.pro0inter.chatserver.model.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {

    List<Message> findBySenderId(int senderId);
    List<Message> findByGroupId(int groupId);
}
