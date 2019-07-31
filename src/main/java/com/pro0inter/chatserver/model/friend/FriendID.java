/* =============== WE Don't NEED This ==========================
package com.pro0inter.chatserver.model.friend;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FriendID implements Serializable {
    private int user_id;
    private int friend_id;

    public FriendID() {
    }

    public FriendID(int user_id, int friend_id) {
        this.user_id = user_id;
        this.friend_id = friend_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    // we need to provide this bcoz its embadeble


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendID friendID = (FriendID) o;
        return user_id == friendID.user_id &&
                friend_id == friendID.friend_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, friend_id);
    }
}
*/
