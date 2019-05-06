package com.example.regiserandloginform.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.regiserandloginform.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etPass, etPass2, etName, etBirthdate;
    Button btnRegisterFinal;

    String urlRegister = "https://o-pointer.000webhostapp.com/register.php";

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etRegisterUsername);
        etPass = findViewById(R.id.etRegisterPass);
        etPass2 = findViewById(R.id.etRegisterPass2);
        etName = findViewById(R.id.etRegisterRealname);
        etBirthdate = findViewById(R.id.etRegisterBirthdate);

        queue = Volley.newRequestQueue(this);

        btnRegisterFinal = findViewById(R.id.btnRegisterFinal);

        btnRegisterFinal.setOnClickListener(v -> startRequest());
    }

    private void startRequest(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlRegister,
                response ->{
                    if(response.length()>1000){
                        new AlertDialog.Builder(this)
                                .setTitle("xxx")
                                .setMessage(response)
                                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
                        ,
                error -> {
                    if(error!=null){
                        new AlertDialog.Builder(this)
                                .setTitle("ddd")
                                .setMessage((CharSequence) error)
                                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("username",etUsername.getText().toString());
                params.put("password",etPass.getText().toString());
                params.put("name",etName.getText().toString());
                params.put("birthdate",etBirthdate.getText().toString());

                return params;
            }
        };
        queue.add(postRequest);
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}