package com.example.regiserandloginform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final TextView etUsername=findViewById(R.id.etUsername);
        final TextView etAge=findViewById(R.id.etAge);
        final TextView tvWelcomeMsg=findViewById(R.id.tvWelcomeMsg);
        final Button bNext=findViewById(R.id.bNext);

        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        String username=intent.getStringExtra("username");
        int age=intent.getIntExtra("age",-1);

        String message="Helló "+name+"!";
        tvWelcomeMsg.setText(message);
        etUsername.setText(username);
        etAge.setText(String.valueOf(age));

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserAreaActivity.this,
                        NavigationActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("username",username);
                intent.putExtra("age",age);

                UserAreaActivity.this.startActivity(intent);

            }
        });
    }
}
