package com.example.regiserandloginform.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.regiserandloginform.R;
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
            login(username,password);
        });
    }

    private void login(String username, String password){
        String urlLogin = "https://o-pointer.000webhostapp.com/login.php?username="+username+"&password="+password;

        RequestQueue requestQueue =  Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, urlLogin,
                null, response -> {
            for (int i=0;i<response.length();i++){
                try {
                    JSONObject jsonObject=response.getJSONObject(i);
                    boolean verified=jsonObject.getBoolean("verified");
                    if (verified){
                        long user_id=jsonObject.getLong("user_id");
                        String name=jsonObject.getString("name");
                        String birthdate=jsonObject.getString("birthdate");
                        User yourself=new User(user_id,username,name,birthdate);
                        Intent intent=new Intent(this,NavigationActivity.class);
                        intent.putExtra("yourself",yourself);
                        startActivity(intent);

                    }
                    else {
                        new AlertDialog.Builder(this)
                                .setTitle("Jelszó probléma")
                                .setMessage("Hibás jelszót adott meg!")
                                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> new AlertDialog.Builder(this)
                .setTitle("Hiba!")
                .setMessage("Nem létező felhasználónév!")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());
        requestQueue.add(jsonArrayRequest);
    }

}
