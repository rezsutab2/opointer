package com.example.regiserandloginform.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.regiserandloginform.R;
import com.example.regiserandloginform.request.LoginRequest;
import com.example.regiserandloginform.pojo.User;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername =  findViewById(R.id.etLoginUsername);
        final EditText etPassword = findViewById(R.id.etLoginPass);
        final Button btnLogin = findViewById(R.id.btnLogin);
        final Button btnRegister=findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            Intent intent=new Intent(this,RegisterActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            final String username = etUsername.getText().toString();
            final String password = etPassword.getText().toString();

            Response.Listener<String> responseListener = response -> {
                    new AlertDialog.Builder(this)
                            .setTitle("xxx")
                            .setMessage(response)
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean executed = jsonResponse.getBoolean("executed");

                    if (executed) {
                        int userid = jsonResponse.getInt("user_id");
                        String name = jsonResponse.getString("name");
                        String birthdate = jsonResponse.getString("birthdate");

                        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                        User user=new User(userid,username,password,name,birthdate);
                        intent.putExtra("user",user);
                        LoginActivity.this.startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };

            LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        });
    }

}
