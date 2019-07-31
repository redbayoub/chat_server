package com.pro0inter.chatserver.model.friendrequest;

import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import java.util.Objects;
import java.util.Random;

@Entity
@Table(name = "friends_requests",uniqueConstraints = @UniqueConstraint(columnNames = {"senderID","receiverId"}))
public class FriendRequest {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private int senderId;

    private int receiverId;

    public FriendRequest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }


    @Override
    public String toString() {
        return "FriendRequest{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                '}';
    }
}
