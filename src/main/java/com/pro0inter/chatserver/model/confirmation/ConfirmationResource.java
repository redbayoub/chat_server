package com.pro0inter.chatserver.model.confirmation;

import com.pro0inter.chatserver.model.user.User;
import com.pro0inter.chatserver.model.user.UserRepository;
import com.pro0inter.chatserver.model.user.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
public class ConfirmationResource {
    @Autowired
    ConfirmationRepository confirmationRepository;

    public Confirmation add_confirmation(int user_id){
        Confirmation confirmation=new Confirmation(user_id);
        System.out.println(confirmation);
        return confirmationRepository.save(confirmation);

    }

    public boolean user_confirmed(int user_id,int confirm_nb){
        Confirmation foundedConfirmation=confirmationRepository.findById(user_id).orElse(null);
        if(foundedConfirmation!=null && foundedConfirmation.getConfirm_nb()==confirm_nb){
            return true;
        }else{
            return false;
        }
    }
    public void delete_confirmation(int user_id){
        confirmationRepository.deleteById(user_id);
    }


}
