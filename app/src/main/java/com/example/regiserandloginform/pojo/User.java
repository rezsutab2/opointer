package com.example.regiserandloginform.pojo;

import java.io.Serializable;

public class User implements Serializable {
    private long user_id;
    private String username;
    private String real_name;

    public User(long user_id,String username){
        this.user_id=user_id;
        this.username=username;
    }

    public User(long user_id, String username, String real_name) {
        this.user_id = user_id;
        this.username = username;
        this.real_name = real_name;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return real_name;
    }

    public long getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        if(real_name!=null){
            return "Felhasználói azonosító:\n"+user_id+
                    "\nFelhasználónév:\n"+username+
                    "\nValódi név:\n"+real_name;
        }
        else {
            return "Felhasználói azonosító:\n"+user_id+
                    "\nFelhasználónév:\n"+username;
        }
    }
}