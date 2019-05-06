package com.example.regiserandloginform.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;

import com.example.regiserandloginform.R;

public class FriendListActivity extends AppCompatActivity {

    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
scrollView=findViewById(R.id.scrollView2);
    }
}
