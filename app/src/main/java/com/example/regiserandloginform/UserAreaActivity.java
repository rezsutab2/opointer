package com.example.regiserandloginform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final EditText etUsername=findViewById(R.id.etUsername);
        final EditText etAge=findViewById(R.id.etAge);
        final TextView tvWelcomeMsg=findViewById(R.id.tvWelcomeMsg);

        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        String username=intent.getStringExtra("username");
        int age=intent.getIntExtra("age",-1);

        String message="Helló "+name+"!";
        tvWelcomeMsg.setText(message);
        etUsername.setText(username);
        etAge.setText(String.valueOf(age));
    }
}
