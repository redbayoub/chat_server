package com.pro0inter.chatserver.model.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pro0inter.chatserver.model.groups.Group;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    private String username;

    @NotBlank
    private String password_sha1;

    @NotBlank
    private String email;

    private boolean is_connected;
    private boolean is_confirmed;
    private String profile_image_path;

    @ManyToMany
    @JsonIgnore
    private List<User> friends=new ArrayList<>();

    @ManyToMany(mappedBy ="members")
    @JsonIgnore
    private List<Group> groups=new ArrayList<>();

    public User() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword_sha1() {
        return password_sha1;
    }

    public void setPassword_sha1(String password_sha1) {
        this.password_sha1 = password_sha1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIs_connected() {
        return is_connected;
    }

    public void setIs_connected(boolean is_connected) {
        this.is_connected = is_connected;
    }

    public String getProfile_image_path() {
        return profile_image_path;
    }

    public void setProfile_image_path(String profile_image_path) {
        this.profile_image_path = profile_image_path;
    }

    public boolean isIs_confirmed() {
        return is_confirmed;
    }

    public void setIs_confirmed(boolean is_confirmed) {
        this.is_confirmed = is_confirmed;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password_sha1='" + password_sha1 + '\'' +
                ", email='" + email + '\'' +
                ", is_connected=" + is_connected +
                ", is_confirmed=" + is_confirmed +
                ", profile_image_path='" + profile_image_path + '\'' +
                '}';
    }
}
