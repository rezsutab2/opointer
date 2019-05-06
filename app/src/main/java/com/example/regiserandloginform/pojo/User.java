package com.example.regiserandloginform.pojo;

import java.io.Serializable;

public class User implements Serializable {
    private long user_id;
    private String username;
    private String password;
    private String real_name;
    private String birth_date;

    public User(long user_id, String username, String password, String real_name, String birth_date) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.real_name = real_name;
        this.birth_date = birth_date;
    }

    public long getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return real_name;
    }

    public String getBirth_date() {
        return birth_date;
    }

}
