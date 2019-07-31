package com.pro0inter.chatserver.model.friendrequest;

import com.pro0inter.chatserver.model.friend.FriendResource;
import com.pro0inter.chatserver.model.user.User;
import com.pro0inter.chatserver.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/friend_requests")
public class FriendRequestResource {
    @Autowired
    FriendRequestRepository friendRequestRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    FriendResource friendResource;
    @PersistenceContext
    EntityManager entityManager;

    @GetMapping("/{receiver_id}")
    public List<FriendRequest> get_users_request(@PathVariable(value="receiver_id") int receiver_id){
        return friendRequestRepo.findByReceiverId(receiver_id);
    }

    @PostMapping
    public ResponseEntity<?> add_friend_request(@Valid @RequestBody FriendRequest fr){
        List<FriendRequest> equalToFrList=entityManager.createQuery("from FriendRequest frs where (frs.senderId=:curr_sender_id AND frs.receiverId=:curr_receiver_id) OR (frs.senderId=:curr_receiver_id AND frs.receiverId=:curr_sender_id) ", FriendRequest.class)
                .setParameter("curr_sender_id", fr.getSenderId())
                .setParameter("curr_receiver_id", fr.getReceiverId())
                .getResultList();
        if(equalToFrList.size()!=0){
            return ResponseEntity.badRequest().build();
        }
        // insert fr to table
       friendRequestRepo.save(fr);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{user_id}")
    public void accept_friend_request(@PathVariable(value="user_id") int user_id,@Valid @RequestBody FriendRequest fr){
        if(user_id==fr.getReceiverId()){
            User senderUser=userRepo.findById(fr.getSenderId()).orElse(null);
            User receiverUser=userRepo.findById(fr.getReceiverId()).orElse(null);
            if(senderUser!=null && receiverUser!=null){
                friendResource.add_friend(fr.getSenderId(), fr.getReceiverId());
                friendRequestRepo.delete(fr);
            }

        }
    }

    @DeleteMapping("/{fr_id}")
    public void decline_friend_request(@PathVariable(value="fr_id") int fr_id){
        friendRequestRepo.deleteById(fr_id);
    }
}
