package com.pro0inter.chatserver.model.friendrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest,Integer> {
     List<FriendRequest> findBySenderId(int sender_id);
     List<FriendRequest> findByReceiverId(int receiver_id);
}

