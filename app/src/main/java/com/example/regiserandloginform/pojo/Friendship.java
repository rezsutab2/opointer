package com.example.regiserandloginform.pojo;

import java.io.Serializable;

public class Friendship implements Serializable {
    int friendship_id;
    int user1_id;
    int user2_id;
    boolean isapproved;

    public Friendship(int friendship_id, int user1_id, int user2_id, boolean isapproved) {
        this.friendship_id = friendship_id;
        this.user1_id = user1_id;
        this.user2_id = user2_id;
        this.isapproved = isapproved;
    }

    public int getFriendship_id() {
        return friendship_id;
    }

    public int getUser1_id() {
        return user1_id;
    }

    public int getUser2_id() {
        return user2_id;
    }

    public boolean isIsapproved() {
        return isapproved;
    }
}
