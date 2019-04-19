package com.example.regiserandloginform;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etAge=findViewById(R.id.etAge);
        final EditText etName=findViewById(R.id.etName);
        final EditText etUsername=findViewById(R.id.etUsername);
        final EditText etPassword=findViewById(R.id.etPassword);

        final Button bRegister=findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=etName.getText().toString();
                final String username=etUsername.getText().toString();
                final String password=etPassword.getText().toString();
                final int age=Integer.parseInt(etAge.getText().toString());

                Response.Listener<String> listener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("Hiba")
                                .setMessage(response)

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(RegisterActivity.this,
                                                LoginActivity.class);
                                        RegisterActivity.this.startActivity(intent);
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success=jsonObject.getBoolean("success");

                            if (success){
                                Intent intent=new Intent(RegisterActivity.this,
                                        LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder
                                        (RegisterActivity.this);
                                builder.setMessage("Nem jött létre a regisztráció")
                                        .setNegativeButton("Újrapróbálkozás",null)
                                        .create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest=new RegisterRequest(name,username,age,password,listener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
