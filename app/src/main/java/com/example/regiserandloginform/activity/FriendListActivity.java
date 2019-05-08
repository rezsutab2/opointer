package com.example.regiserandloginform.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.regiserandloginform.R;
import com.example.regiserandloginform.pojo.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FriendListActivity extends AppCompatActivity {

    LinearLayout layoutFriends;
    Animation textClick;
    Button btnSendFriendRequest;
    RequestQueue queue;
    String user2name;
    User loggedInUser;
    long your_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        layoutFriends=findViewById(R.id.layoutFriends);
        textClick = new AlphaAnimation(1.0f, 0.4f);
        textClick.setDuration(1000);
        textClick.setRepeatCount(1);

        queue=Volley.newRequestQueue(this);

        btnSendFriendRequest=findViewById(R.id.btnSendFriendRequest);

        loggedInUser= (User) getIntent().getSerializableExtra("user");
        your_id=loggedInUser.getUser_id();

        friendship(your_id);

        btnSendFriendRequest.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Barát felvétele");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);
            builder.setPositiveButton("OK", (dialog, which) -> {
                user2name = input.getText().toString();
                startRequest(your_id,user2name);
                Toast.makeText(FriendListActivity.this,"Barátkérelem elküldve!",Toast.LENGTH_LONG);
            });
            builder.setNegativeButton("Mégse", (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }

    private void friendship(long your_id){

        String urlFriendship="https://o-pointer.000webhostapp.com/friendship.php?user_id="+your_id;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, urlFriendship,
                null, response -> {
            for (int i=0;i<response.length();i++){
                try {
                    JSONObject jsonObject=response.getJSONObject(i);
                    long user2_id=jsonObject.getLong("user_id");
                    String username=jsonObject.getString("username");
                    String real_name=jsonObject.getString("name");
                    String birthdate=jsonObject.getString("birthdate");
                    User user=new User(user2_id,username,real_name,birthdate);
                    TextView textView=new TextView(this);
                    if (jsonObject.getInt("isapproved")==1){
                        layoutFriends.addView(textView);
                        textView.setText(user.toString());
                        textView.setPadding(0,40,0,40);
                        textView.setTextColor(Color.parseColor("#ffffff"));
                        textView.setBackgroundResource(R.drawable.rainbow_blue);
                        textView.setGravity(Gravity.CENTER|Gravity.CENTER);
                    }
                    else {
                        layoutFriends.addView(textView);
                        textView.setText(user.toString()+"\n\n Ez a felhasználó barátnak jelölt. " +
                                "Elfogadáshoz tegye legalább két ujját a címkére!");
                        textView.setPadding(0,40,0,40);
                        textView.setTextColor(Color.parseColor("#000000"));
                        textView.setBackgroundResource(R.drawable.xflareone);
                        textView.setGravity(Gravity.CENTER|Gravity.CENTER);


                        textView.setOnClickListener(v -> { });

                        textClick.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                                DialogInterface.OnClickListener dialogClickListener = (dialog, button) -> {
                                    switch (button){
                                        case DialogInterface.BUTTON_POSITIVE:
                                            acceptRequest(user2_id);
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:

                                            break;
                                        case DialogInterface.BUTTON_NEUTRAL:

                                            break;
                                    }
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(FriendListActivity.this);
                                builder.setMessage("Elfogadod, vagy elutasítod a felkérést?").setPositiveButton("Igen", dialogClickListener)
                                        .setNegativeButton("Nem", dialogClickListener).setNeutralButton("Mégse",dialogClickListener).show();
                            }
                        });
                        textView.setOnTouchListener((v, event) -> {
                            switch(event.getAction() & MotionEvent.ACTION_MASK){
                                case MotionEvent.ACTION_POINTER_DOWN:
                                    textView.setAnimation(textClick);
                                    textView.startAnimation(textClick);
                                    break;
                                case MotionEvent.ACTION_POINTER_UP:
                                    textView.clearAnimation();
                                    break;
                            }
                            return true;
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(error.getMessage())
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void startRequest(long userid,String user2name){
        String urlSendRequest="https://o-pointer.000webhostapp.com/sendrequest.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlSendRequest,
                response ->{
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setMessage("Kérelem elküldve");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            (dialog, which) -> dialog.dismiss());
                    alertDialog.show();
                }
                ,
                error -> {
                }) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("userid", String.valueOf(userid));
                params.put("user2name", user2name);
                return params;
            }
        };
        queue.add(postRequest);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private void acceptRequest(long user2_id){
        String urlSendRequest="https://o-pointer.000webhostapp.com/acceptrequest.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlSendRequest,
                response ->{
                }
                ,
                error -> {
                }) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("user2_id", String.valueOf(user2_id));
                return params;
            }
        };
        queue.add(postRequest);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}
