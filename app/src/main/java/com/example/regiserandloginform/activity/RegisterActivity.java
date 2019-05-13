package com.example.regiserandloginform.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.regiserandloginform.R;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etPass, etPass2, etName;
    Button btnRegisterFinal;

    String urlRegister = "https://o-pointer.000webhostapp.com/register.php";
    String pass, pass2, username, name;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etRegisterUsername);
        etPass = findViewById(R.id.etRegisterPass);
        etPass2 = findViewById(R.id.etRegisterPass2);
        etName = findViewById(R.id.etRegisterRealname);

        queue = Volley.newRequestQueue(this);

        btnRegisterFinal = findViewById(R.id.btnRegisterFinal);

        btnRegisterFinal.setOnClickListener(v ->{
            startRequest();
        });
    }

    private void startRequest() {
        pass = etPass.getText().toString().replaceAll("\\s","");
        pass2 = etPass2.getText().toString().replaceAll("\\s","");
        username = etUsername.getText().toString().replaceAll("\\s","");
        name = etName.getText().toString().replaceAll("\\s","");
        if (!pass.equals(pass2)) {
            new AlertDialog.Builder(this)
                    .setTitle("Jelszóegyezési hiba!")
                    .setMessage("A két jelszó nem egyezik. Próbáld újra!")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            etPass.setText("");
            etPass2.setText("");
            return;
        }
        if (pass.equals("") || pass2.equals("") || username.equals("") || name.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("Kitöltési hiba!")
                    .setMessage("Kérlek tölts ki minden mezőt és próbáld újra!")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return;
        }
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlRegister,
                response -> new AlertDialog.Builder(this)
                        .setMessage("Sikeres regisztráció!")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            Intent intent = new Intent(this, LoginActivity.class);
                            startActivity(intent);
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                ,
                error -> {
                    if (error != null) {
                        new AlertDialog.Builder(this)
                                .setTitle("Hiba!")
                                .setMessage(error.getMessage())
                                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", etUsername.getText().toString());
                params.put("password", etPass.getText().toString());
                params.put("name", etName.getText().toString());

                return params;
            }
        };
        queue.add(postRequest);
    }
}