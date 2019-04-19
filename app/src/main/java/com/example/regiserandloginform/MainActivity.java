package com.example.regiserandloginform;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etUsername=findViewById(R.id.etUsername);
        final EditText etPassword=findViewById(R.id.etPassword);

        final Button bLogin=findViewById(R.id.bLogin);
        final TextView tvRegisterhere=findViewById(R.id.tvRegisterhere);

        tvRegisterhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(MainActivity.this,RegisterActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=etUsername.getText().toString();
                final String password=etPassword.getText().toString();

                Response.Listener<String> listener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AlertDialog.Builder builderresponse = new AlertDialog.Builder
                                (MainActivity.this);
                        builderresponse.setMessage(response)
                                .setNegativeButton("Újrapróbálkozás",null)
                                .create().show();
                        try {
                            JSONObject jsonObject=new JSONObject(response);

                            boolean success=jsonObject.getBoolean("success");

                            if (success){
                                String name=jsonObject.getString("name");
                                int age=jsonObject.getInt("age");

                                Intent intent=new Intent(MainActivity.this,
                                        UserAreaActivity.class);
                                intent.putExtra("name",name);
                                intent.putExtra("username",username);
                                intent.putExtra("age",age);

                                MainActivity.this.startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder
                                        (MainActivity.this);
                                builder.setMessage("Nem sikerült bejelentkezni.")
                                        .setNegativeButton("Újrapróbálkozás",null)
                                        .create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest=new LoginRequest(username,password,listener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
