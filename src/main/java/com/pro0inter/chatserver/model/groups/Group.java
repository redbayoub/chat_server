package com.pro0inter.chatserver.model.groups;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pro0inter.chatserver.model.user.User;
import org.hibernate.SessionFactory;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Table(name = "groups")
public class Group {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int group_id;
    @NotBlank
    private String name;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "Group_User",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "id")}
        )
    List<User> members=new ArrayList<>();

    public Group() {
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_aid) {
        this.group_id = group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Group{" +
                "group_id=" + group_id +
                ", name='" + name + '\'' +
                ", members=" + members +
                '}';
    }
}
