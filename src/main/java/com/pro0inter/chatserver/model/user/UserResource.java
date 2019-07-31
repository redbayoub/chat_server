package com.pro0inter.chatserver.model.user;

import com.pro0inter.chatserver.model.confirmation.Confirmation;
import com.pro0inter.chatserver.model.confirmation.ConfirmationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ConfirmationResource confirmationResource;

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping("/{id}")
    public User getUser(@PathVariable(value = "id") int user_id){
        return userRepository.findById(user_id).orElse(new User());
    }

    @GetMapping("/{curr_user_id}/search/{username}")
    public List<User> serach_by_username(@PathVariable(value = "curr_user_id") int curr_user_id,@PathVariable(value = "username") String username){
        List<User> resultList = entityManager.createNativeQuery("select * from users u where u.id!=:curr_user_id AND u.username REGEXP :username",User.class)
                .setParameter("username", "^"+username)
                .setParameter("curr_user_id", curr_user_id)
                .getResultList();
        return resultList;
    }

    @PostMapping("/signUp")
    public User signUp(@RequestBody @Valid User user){
        if(userRepository.findByEmail(user.getEmail())==null){
            User savedUser=userRepository.save(user);
            Confirmation conf=confirmationResource.add_confirmation(savedUser.getId());
            // TODO send confirm nb to user email
            return savedUser;
        }else{
            String err_msg= "Your Email is Connected to Another Profile";
            User err_user=new User();
            err_user.setUsername(err_msg);
            return err_user;
        }
    }

    @PutMapping("/confirm/{id}")
    public Boolean confirm_user(@PathVariable(value="id") int user_id, @RequestBody int confirm_nb){
        if(confirmationResource.user_confirmed(user_id, confirm_nb)){
            User currUser=userRepository.findById(user_id).orElse(null);
            currUser.setIs_confirmed(true);
            userRepository.save(currUser);
            confirmationResource.delete_confirmation(user_id);
            return true;
        }else{
            return false;
        }
    }

    @PutMapping("/login")
    public User login(@RequestBody User user){
        User selUser=userRepository.findByEmail(user.getEmail());
        String err_msg;
        if(selUser!=null){
            if ( selUser.getPassword_sha1().equals(user.getPassword_sha1())) {
                selUser.setIs_connected(true);
                userRepository.save(selUser);
                return selUser;
            }else {
                err_msg= "Wrong password";
            }
        }else {
            err_msg= "Email Not Found";
        }
        User err_user=new User();
        err_user.setUsername(err_msg);
        return err_user;
    }

    @PutMapping("/logout")
    public void logout(@RequestBody User user){
        User selUser=userRepository.findByEmail(user.getEmail());
        if(selUser!=null && selUser.getPassword_sha1().equals(user.getPassword_sha1())){
            selUser.setIs_connected(false);
            userRepository.save(selUser);
        }
    }

    @PutMapping("/update/{id}")
    public User update_user(@PathVariable("id") int user_id,@RequestBody User new_user_data){
        User curr_user=userRepository.findById(user_id).orElse(null);
        if(curr_user!=null){
            if(new_user_data.getUsername()!=null&& !new_user_data.getUsername().isEmpty()){
                curr_user.setUsername(new_user_data.getUsername());
            }
            if(new_user_data.getEmail()!=null&& !new_user_data.getEmail().isEmpty()){
                curr_user.setEmail(new_user_data.getEmail());
            }
            if(new_user_data.getPassword_sha1()!=null&& !new_user_data.getPassword_sha1().isEmpty()){
                curr_user.setPassword_sha1(new_user_data.getPassword_sha1());
            }
            if(new_user_data.getProfile_image_path()!=null&& !new_user_data.getProfile_image_path().isEmpty()){
                curr_user.setProfile_image_path(new_user_data.getProfile_image_path());
            }
            return userRepository.save(curr_user);
        }else{
            return new User();
        }

    }
}
