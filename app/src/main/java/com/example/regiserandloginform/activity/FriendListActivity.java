package com.example.regiserandloginform.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    long your_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        layoutFriends=findViewById(R.id.layoutFriends);
        textClick = new AlphaAnimation(1.0f, 0.4f);
        textClick.setDuration(1000);

        queue=Volley.newRequestQueue(this);

        long your_id=getIntent().getLongExtra("user_id",29);

        friendship();
    }

    private void friendship(){

        String urlFriendship="https://o-pointer.000webhostapp.com/friendship.php?user_id="+your_id;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, urlFriendship,
                null, response -> {
            for (int i=0;i<response.length();i++){
                try {
                    JSONObject jsonObject=response.getJSONObject(i);
                    long user_id=jsonObject.getLong("user_id");
                    String username=jsonObject.getString("username");
                    String real_name=jsonObject.getString("name");
                    String birthdate=jsonObject.getString("birthdate");
                    User user=new User(user_id,username,real_name,birthdate);
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
                                DialogInterface.OnClickListener dialogClickListener = (dialog, button) -> {
                                    switch (button){
                                        case DialogInterface.BUTTON_POSITIVE:

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

                            @Override
                            public void onAnimationRepeat(Animation animation) {

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
                        //Textview hátterek a https://uigradients.com -ról
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

    private void startRequest(long userid,long user2id){
        String urlSendRequest="https://o-pointer.000webhostapp.com/friendship.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlSendRequest,
                response ->{
                }
                ,
                error -> {
                }) {

            @Override
            protected Map<String, String> getParams()
            {

                //USER2NAME KEEEEEEEEEEEEEEEELLLLLLLLLL
                Map<String, String>  params = new HashMap<>();
                params.put("userid", String.valueOf(userid));
                params.put("user2id", String.valueOf(user2id));
                return params;
            }
        };
        queue.add(postRequest);
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

}
