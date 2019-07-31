package com.pro0inter.chatserver.model.confirmation;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Random;

@Entity
@Table(name = "user_confirmations")
public class Confirmation {
    @Id
    private int user_id;

    private int confirm_nb;

    public Confirmation() {
    }

    public Confirmation(int user_id) {
        this.user_id = user_id;
        this.confirm_nb=generate_confirm_nb();
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getConfirm_nb() {
        return confirm_nb;
    }

    public void setConfirm_nb(int confirm_nb) {
        this.confirm_nb = confirm_nb;
    }

    @Override
    public String toString() {
        return "Confirmation{" +
                "user_id=" + user_id +
                ", confirm_nb=" + confirm_nb +
                '}';
    }

    private int generate_confirm_nb(){
        Random random=new Random(System.nanoTime());
        int conf_nb=0;
        for(int i=0;i<6;i++){
            conf_nb+=(conf_nb*10)+random.nextInt(9);
        }
        return conf_nb;
    }
}
