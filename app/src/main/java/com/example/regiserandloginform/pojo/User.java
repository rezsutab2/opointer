package com.example.regiserandloginform.pojo;

import android.content.Intent;

import java.io.Serializable;

public class User implements Serializable {
    private long user_id;
    private String username;
    private String real_name;
    private String birth_date;

    public User(long user_id, String username, String real_name, String birth_date) {
        this.user_id = user_id;
        this.username = username;
        this.real_name = real_name;
        this.birth_date = birth_date;
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

    public void setUserSerializedExtra(Intent intent){
        intent.putExtra("user",this);
    }

    public void getUserSerializedExtra(Intent intent){
        intent.getSerializableExtra("user");
    }

    @Override
    public String toString() {
        return "Felhasználói azonosító:\n"+user_id+
                "\nFelhasználónév:\n"+username+
                "\nNév:\n"+real_name+
                "\nSzületési dátum:\n"+birth_date;
    }
}