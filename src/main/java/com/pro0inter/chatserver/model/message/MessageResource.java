package com.pro0inter.chatserver.model.message;

import com.pro0inter.chatserver.model.user.User;
import com.pro0inter.chatserver.model.user.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/messages")
public class MessageResource {
    private static final int LIMIT = 10 ;
    @Autowired
    MessageRepository messageRepo;
    @Autowired
    UserResource userResource;
    @PersistenceContext
    EntityManager entityManager;

    @PostMapping
    public Message add_message(@RequestBody Message message){
        User user=userResource.getUser(message.getSenderId());
        if(user!=null&&user.getId()!=0){
            return messageRepo.save(message);
        }else{
            return new Message();
        }
    }

    // The Diff btw before and after is < , >

    @PutMapping("/before/{receiver_id}")
    public List<Message> get_before_messages(@PathVariable(value="receiver_id") int receiver_id,@RequestBody Message template){

        User recciver_user=userResource.getUser(receiver_id);
        if (recciver_user==null || recciver_user.getId()==0) return new ArrayList<>();
        if (template.getSenderId()!=0){ // fetch chat messages
            return entityManager.createNativeQuery("SELECT * FROM ( " +
                            "SELECT * from messages as msg " +
                            "where ( ( msg.sender_id=:sender_id AND  msg.receiver_id=:receiver_id)OR (msg.sender_id=:receiver_id AND msg.receiver_id=:sender_id )) AND msg.sending_date<:sending_date ORDER BY msg.sending_date DESC limit :limit " +
                            ") as tempo ORDER BY tempo.sending_date ASC ",
                    Message.class)
                    .setParameter("sender_id", template.getSenderId())
                    .setParameter("receiver_id", template.getReceiver_id())
                    .setParameter("sending_date",template.getSending_date(),TemporalType.TIMESTAMP)
                    .setParameter("limit", LIMIT)
                    .getResultList();
        }else{
            if(template.getGroupId()!=0){ // fetch group messages
                return entityManager.createNativeQuery("SELECT * FROM ( " +
                                "SELECT * from messages as msg " +
                                "where msg.group_id=:groupId AND msg.sending_date<:sending_date ORDER BY msg.sending_date DESC limit :limit " +
                                ") as tempo ORDER BY tempo.sending_date ASC ",
                        Message.class)
                        .setParameter("groupId", template.getGroupId())
                        .setParameter("sending_date", template.getSending_date(), TemporalType.TIMESTAMP)
                        .setParameter("limit", LIMIT)
                        .getResultList();

            }else{
                return new ArrayList<>();
            }
        }
    }

    @PutMapping("/after/{receiver_id}")
    public List<Message> get_after_messages(@PathVariable(value="receiver_id") int receiver_id,@RequestBody Message template){

        User recciver_user=userResource.getUser(receiver_id);
        if (recciver_user==null || recciver_user.getId()==0) return new ArrayList<>();
        if (template.getSenderId()!=0){ // fetch chat messages
            return entityManager.createQuery("from Message msg " +
                            "where msg.senderId=:senderId AND msg.receiver_id=:receiver_id AND msg.sending_date>:sending_date ORDER BY msg.sending_date ASC",
                    Message.class)
                    .setParameter("senderId", template.getSenderId())
                    .setParameter("receiver_id", template.getReceiver_id())
                    .setParameter("sending_date",template.getSending_date(), TemporalType.TIMESTAMP)
                    .setMaxResults(LIMIT)
                    .getResultList();
        }else{
            if(template.getGroupId()!=0){ // fetch group messages
                return entityManager.createQuery("from Message msg " +
                                "where msg.groupId=:groupId AND msg.sending_date>:sending_date ORDER BY msg.sending_date ASC ",
                        Message.class)
                        .setParameter("groupId", template.getGroupId())
                        .setParameter("sending_date", template.getSending_date(), TemporalType.TIMESTAMP)
                        .setMaxResults(LIMIT)
                        .getResultList();

            }else{
                return new ArrayList<>();
            }

        }
    }

    @GetMapping("/{msg_id}")
    public Message getMessageByID(@PathVariable(value = "msg_id") int msg_id){
        return messageRepo.findById(msg_id).orElse(new Message());
    }

    @GetMapping("/recent/{receiver_id}")
    public List<Message> get_recent_messages(@PathVariable(value="receiver_id") int receiver_id){
        User recciver_user=userResource.getUser(receiver_id);
        if (recciver_user!=null && recciver_user.getId()!=0)
            return entityManager.createQuery("from Message msg " +
                        "where msg.receiver_id=:receiver_id GROUP BY msg.senderId ORDER BY msg.sending_date ASC ",
                Message.class)
                .setParameter("receiver_id", receiver_id)
                .getResultList();
        else
            return new ArrayList<>();
    }



}
