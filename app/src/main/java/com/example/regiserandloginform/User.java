package com.example.regiserandloginform;

import java.util.ArrayList;

public class User {
    private int id;
    private String username;

    public User(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getFriends(){
        ArrayList<String> friendlist=new ArrayList<String>();
        return friendlist;
    }
}
