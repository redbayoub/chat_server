/* =============== WE Don't NEED This ==========================
package com.pro0inter.chatserver.model.friend;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "friends")
public class Friend  {

    @EmbeddedId
    private FriendID friendId;

    public Friend() {
    }

    public FriendID getFriendId() {
        return friendId;
    }

    public void setFriendId(FriendID friendId) {
        this.friendId = friendId;
    }
}
*/
