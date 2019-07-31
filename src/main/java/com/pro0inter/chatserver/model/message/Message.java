package com.pro0inter.chatserver.model.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@Table(name = "messages")
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = {"sending_date"},allowGetters = true)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private int senderId;

    private int receiver_id;
    private int groupId;
    @NotBlank
    private String content;
    private boolean isFile;



    private Date sending_date;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    public Date getSending_date() {
        return sending_date;
    }

    public void setSending_date(Date sending_date) {
        this.sending_date = sending_date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", receiver_id=" + receiver_id +
                ", groupId=" + groupId +
                ", content='" + content + '\'' +
                ", isFile=" + isFile +
                ", sending_date=" + sending_date +
                '}';
    }
}
