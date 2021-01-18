package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class UserNotification extends AbstractEntity {

    private String text;
    @ManyToOne(targetEntity=User.class)
    private User sender;
    @Nullable
    private Integer receiverId;
    private Date createdAt;

    public UserNotification() {}

    public UserNotification(String text, User sender, Integer receiverId, Date createdAt) {
        this.text = text;
        this.sender = sender;
        this.receiverId = receiverId;
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
