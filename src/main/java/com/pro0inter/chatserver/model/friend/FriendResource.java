package com.pro0inter.chatserver.model.friend;

import com.pro0inter.chatserver.model.user.User;
import com.pro0inter.chatserver.model.user.UserRepository;
import com.pro0inter.chatserver.model.user.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendResource {
/*    @Autowired
    FriendRepository friendRepo;*/
    @Autowired
    UserResource userResource;
    @Autowired
    UserRepository userRepo;

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping("/{id}")
    public List<User> getFriends(@PathVariable("id") int id){
       /* return entityManager
                .createNativeQuery("select u.id,u.username,u.password_sha1,u.email,u.is_connected,u.is_confirmed,u.profile_image_path " +
                        " from users as u" +
                        " inner join (select friends.friend_id from friends where friends.user_id=:id) as fr " +
                        " on u.id=fr.friend_id " +
                        " ORDER BY u.is_connected DESC", User.class)
                .setParameter("id", id)
                .getResultList();*/
       return userResource.getUser(id).getFriends();
    }

    public void add_friend(int user_id,int friend_id){
        /*FriendID friend_id1=new FriendID(user_id,friend_id);
        FriendID friend_id2=new FriendID(friend_id,user_id);
        Friend friend=new Friend();
        friend.setFriendId(friend_id1);
        Friend friend2=new Friend();
        friend2.setFriendId(friend_id2);
        friendRepo.saveAll(Arrays.asList(friend,friend2));*/
        User user=userRepo.findById(user_id).orElse(new User());
        User friend=userRepo.findById(friend_id).orElse(new User());
        if(user.getId()!=0 && friend.getId()!=0){
            user.getFriends().add(friend);
            friend.getFriends().add(user);
            userRepo.saveAll(Arrays.asList(user,friend));
        }
    }

    @GetMapping("/{curr_user_id}/search/{username}")
    public List<User> serach_by_username(@PathVariable(value = "curr_user_id") int curr_user_id,@PathVariable(value = "username") String username){
        List<User> resultList = entityManager.createNativeQuery("select * from users u where u.id!=:curr_user_id AND u.username REGEXP :username",User.class)
                .setParameter("username", "^"+username)
                .setParameter("curr_user_id", curr_user_id)
                .getResultList();
        return resultList;
    }

}
